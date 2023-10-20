import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.signup_project.User
import com.example.signup_project.databinding.ItemUserBinding


class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        private val userNameTextView: TextView = binding.nameTextView
        private val userPhoneTextView: TextView = binding.phoneTextView
        private val userEmailTextView: TextView = binding.emailTextView

        fun bind(user: User) {
            userNameTextView.text = "Name: ${user.name}"
            userPhoneTextView.text = "Phone: ${user.phone}"
            userEmailTextView.text = "Email: ${user.email}"

            // Set an OnClickListener on the name TextView
            userNameTextView.setOnClickListener {
                // Open the phone dialer with the user's phone number
                val phoneNumber = user.phone
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:$phoneNumber")
                it.context.startActivity(dialIntent)
            }
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}