package course.examples.Services.KeyClient;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<ExampleItem> mExampleList;
    private OnSongListener mOnSongListener;

    //Storing all the references from view and passing them to myadapter

    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        OnSongListener onSongListener;

        public ExampleViewHolder(@NonNull View itemView, OnSongListener onSongListener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            this.onSongListener = onSongListener;
            itemView.setOnClickListener(this);
        }

        public void onClick(View view){
            onSongListener.onSongClick(getAdapterPosition());
        }
    }

    public interface OnSongListener{
        void onSongClick(int position);
    }

    //Creates views using layout inflator and returns those views to myviewholder
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mOnSongListener);
        return evh;
    }



    public ExampleAdapter(ArrayList<ExampleItem> exampleList, OnSongListener onSongListener) {
        mExampleList = exampleList;
        mOnSongListener = onSongListener;

    }
    //Binds the views with the data obtained from the arraylist data
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        holder.mImageView.setImageBitmap(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());

    }

    //Returns the size of array

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
