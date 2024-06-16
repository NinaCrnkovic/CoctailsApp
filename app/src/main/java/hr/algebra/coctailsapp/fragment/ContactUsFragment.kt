package hr.algebra.coctailsapp.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import hr.algebra.coctailsapp.R


class ContactUsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_us, container, false)

        val sendEmailButton = view.findViewById<Button>(R.id.button_send_email)
        sendEmailButton.setOnClickListener {
            sendEmail()
        }

        return view
    }

    private fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("thecoctailcorenr@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Inquiry from The Cocktail Corner")
            putExtra(Intent.EXTRA_TEXT, "Hello,\n\nI would like to inquire about...")
            `package` = "com.google.android.gm"
        }

        try {
            startActivity(emailIntent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Gmail app is not installed", Toast.LENGTH_SHORT).show()
        }
    }
}

