package app.iamin.iamin.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.iamin.iamin.R;
import app.iamin.iamin.model.Need;
import app.iamin.iamin.util.UiUtils;
import io.realm.RealmResults;

/**
 * Created by Paul on 10-10-2015.
 */
public class NeedsAdapter extends RecyclerView.Adapter<NeedsAdapter.ViewHolder> {

    private Context mContext;
    private RealmResults<Need> mNeeds;

    public NeedsAdapter(Context context) {
        mContext = context;
        setHasStableIds(true);
    }

    @Override
    public NeedsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        NeedView view = (NeedView) inflater.inflate(R.layout.need_item, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(NeedsAdapter.ViewHolder holder, int position) {
        Need need = mNeeds.get(position);
        holder.mNeedView.setNeed(need);
    }

    @Override
    public int getItemCount() {
        return mNeeds == null ? 0 : mNeeds.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(RealmResults<Need> needs) {
        mNeeds = needs;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        NeedView mNeedView;
        ItemClickListener mClickListener;

        public ViewHolder(NeedView needView, ItemClickListener clickListener) {
            super(needView);
            mNeedView = needView;
            mNeedView.setClickable(true);
            mNeedView.setOnClickListener(this);
            mClickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private final ItemClickListener clickListener = new ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = UiUtils.getDetailIntent(mContext, mNeeds.get(position));
            mContext.startActivity(intent);
        }
    };
}