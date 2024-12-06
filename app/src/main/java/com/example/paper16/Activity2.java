package com.example.paper16;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import io.paperdb.Paper;

public class Activity2 extends AppCompatActivity {

    Button edit, delete, exit;
    EditText name, discr, price, img;
    ImageView imgg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        edit = findViewById(R.id.editButton);
        delete = findViewById(R.id.deleteButton);
        exit = findViewById(R.id.exitButton);
        name = findViewById(R.id.Name);
        discr = findViewById(R.id.Description);
        price = findViewById(R.id.Price);
        img = findViewById(R.id.Image);
        imgg = findViewById(R.id.imgg);

        String select = getIntent().getStringExtra("book");
        if (select != null) {
            Book book = Paper.book().read(select);
            if (book != null) {
                name.setText(book.getName());
                discr.setText(book.getDescription());
                price.setText(book.getPrice().toString());
                img.setText(book.getImage());

                String imggg = book.getImage();
                int imga = getResources().getIdentifier(imggg, "drawable", getPackageName());
                imgg.setImageResource(imga);
            }
        }

        // Кнопка выхода
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Кнопка редактирования
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Descr = discr.getText().toString();
                String Price = price.getText().toString();
                String Img = img.getText().toString();

                if (Name.isEmpty() || Descr.isEmpty() || Price.isEmpty()) {
                    Toast.makeText(Activity2.this, "Необходимо заполнить все", Toast.LENGTH_SHORT).show();
                    return;
                }

                float Pricee;
                try {
                    Pricee = Float.parseFloat(Price);
                } catch (NumberFormatException e) {
                    Toast.makeText(Activity2.this, "Введите числовое значение", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Name.equals(select)) {
                    Paper.book().delete(select);
                }

                Book cl = new Book(null, Name, Descr, Pricee, Img);
                Paper.book().write(Name, cl);

                Toast.makeText(Activity2.this, "Обновление успешно", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity2.this, MainActivity.class);
                startActivity(intent);
            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                if (!Name.isEmpty()) {
                    Paper.book().delete(select);
                    Toast.makeText(Activity2.this, "Все удалено", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Activity2.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Activity2.this, "Нельзя удалять пустоту", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}