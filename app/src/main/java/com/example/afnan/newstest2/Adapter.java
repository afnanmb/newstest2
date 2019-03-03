package com.example.afnan.newstest2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.afnan.newstest2.models.Artical;

import java.util.List;

//بنعمل استدعاء لمكتبه لتحميل الصور في الاندرويد سواء من الجهاز او من علي النت
//RequestOptions اظن عشان نعمل ريكويست عشان نحمل الصوره


//make adapter which is intermediate between data and screen
//then import methods هيظهر علامه اللمبهو نختار دا
//MyViewHolder have class below
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    //call the article class that we made
    //make constructor for  List<Artical> and Context by click right then choose generate then construct
    private List<Artical> articals;
    private Context context;
    //OnItemClickListener:Interface definition for a callback
    // to be invoked when an item in this AdapterView has been clicked.
    //https://antonioleiva.com/recyclerview-listener/
    private OnItemClickListener OnItemClickListener;

    public Adapter(List<Artical> articals, Context context) {
        this.articals = articals;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //ضيفنا parent عشان تبقي شبه onCreateViewHolder
        //inflate read the xml file and convert it to java code
        View view = LayoutInflater.from(context).inflate(R.layout.item , parent ,false);
        return new MyViewHolder(view ,OnItemClickListener);
    }

    @Override
    //https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {

        final MyViewHolder holder =holders;
        Artical model = articals.get(position);

        //make import all files in the top like Glide مكتبه بتحمل الصور

        RequestOptions requestOptions = new RequestOptions();
        //Sets an Drawable to display while a resource is loading.
        RequestOptions.placeholderOf(Utils.getRandomDrawbleColor());
        //Sets a Drawable to display if a load fails.
        RequestOptions.errorOf(Utils.getRandomDrawbleColor());
        //.ALL:Caches remote data with both DATA and RESOURCE, and local data with RESOURCE only.
        RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL);
        RequestOptions.centerCropTransform();

        Glide.with(context)
                .load(model.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        //https://www.ibm.com/support/knowledgecenter/en/SSS28S_8.2.0/API/api_reference_holder_objects_java-filter_java.html
                        //GONE:This view is invisible, and it doesn't take any space for layout purposes.
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                //https://developer.android.com/reference/android/transition/package-summary
                // DrawableTransitionOptions.withCrossFade:Enables a cross fade animation between both
                // the placeholder and the first resource and between subsequent resources (of thumbnails are used)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        //A model: object is a type of object that contains the data of an application,
        // provides access to that data, and implements logic to manipulate the data
        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        //.getSystemId() he made it in video .getname but it make error
        holder.source.setText(model.getSource().getSystemId());
        //\u2022:•
        holder.time.setText("\u2022" + Utils.DateToTimeFormat(model.getPublishedAt()));
        holder.publishedAt.setText(Utils.DateFormat(model.getPublishedAt()));
        holder.author.setText(model.getAuthor());


    }

    @Override
    public int getItemCount() {
        return articals.size();
    }

    public void OnItemClickListener(OnItemClickListener OnItemClickListener){
        this.OnItemClickListener = OnItemClickListener;
    }

    public interface OnItemClickListener{

        //View: The view within the AdapterView that was clicked (this will be a view provided by the adapter)
        //int: The position of the view in the adapter.
        //https://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener
        //للي بين القوسين في السطر اللي تحت دا هننفذوا جواه
        void OnItemClick(View view,int position);
    }
    //make implement method
    //make constructor اللمبه هتكون محمره اختار دي
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //الحاجات دي اللي عملنها في ال layout-item.xml
        //هنعرفها و نديها اسماء اي حاجه بس الاحسن زي اللي هناك
        TextView title, desc, author, publishedAt, source, time;
        ImageView imageView;
        ProgressBar progressBar;
        //دي مش عارفه هي ايه
        OnItemClickListener OnItemClickListener;

        //اللي بين القوسين في السطر اللي تحت دا هننفذوا جواه
        public MyViewHolder(@NonNull View itemView , OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            //هندور علي ال id بتاعها اللي هناك
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.progress_load_photo);

            this.OnItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {

            OnItemClickListener.OnItemClick(v ,getAdapterPosition());

        }
    }
}
