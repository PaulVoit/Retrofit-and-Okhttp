package ru.jo4j.retrofitexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private final List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private Post mPost;
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.view = itemView;
            textView = view.findViewById(R.id.item_post);
        }

        public void bind(Post post) {
            mPost = post;
            String content = String.format("ID: %s \n user ID: %s \n Title: %s \n Text: %s \n\n",
                    post.getId(), post.getUserId(), post.getTitle(), post.getText());
            textView.append(content);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        return posts.size();
    }
}

