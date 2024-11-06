package fpoly.md19304.btvn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;
    private OnEditClickListener editListener;

    // Interface để xử lý sự kiện click vào item
    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    // Interface để xử lý sự kiện click vào nút xóa
    public interface OnDeleteClickListener {
        void onDeleteClick(User user);
    }

    // Interface để xử lý sự kiện click vào nút sửa
    public interface OnEditClickListener {
        void onEditClick(User user);
    }

    // Constructor
    public UserAdapter(List<User> userList, OnItemClickListener listener,
                       OnDeleteClickListener deleteListener, OnEditClickListener editListener) {
        this.userList = userList;
        this.listener = listener;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvName.setText(user.getName());
        holder.tvCity.setText(user.getCity());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(user));

        // Nút sửa
        holder.btnEdit.setOnClickListener(v -> editListener.onEditClick(user));

        // Nút xóa
        holder.btnDelete.setOnClickListener(v -> deleteListener.onDeleteClick(user));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // Cập nhật danh sách người dùng
    public void setUserList(List<User> users) {
        this.userList = users;
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCity;
        ImageButton btnEdit, btnDelete;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCity = itemView.findViewById(R.id.tvCity);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
