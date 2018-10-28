package com.example.myapplication;
	import android.database.Cursor;
	import android.support.v7.app.AlertDialog;
	import android.support.v7.app.AppCompatActivity;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.Button;
	import android.widget.EditText;
	import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

     	    myHelper myDb;
	  EditText editTextId, editName,editSurname,editMarks;
	  Button btnAddData, btngetData, btnUpdate, btnDelete, btnviewAll;

     	    @Override
	   protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            myDb = new myHelper(this);

            editTextId = (EditText) findViewById(R.id.editText_id);
            editName = (EditText) findViewById(R.id.editText_name);
            editSurname = (EditText) findViewById(R.id.editText_surname);
            editMarks = (EditText) findViewById(R.id.editText_Marks);
            btnAddData = (Button) findViewById(R.id.button_add);
            btngetData = (Button) findViewById(R.id.button_view);
            btnviewAll = (Button) findViewById(R.id.button_viewAll);
            btnUpdate = (Button) findViewById(R.id.button_update);
            btnDelete = (Button) findViewById(R.id.button_delete);
            AddData();
            getData();
            updateData();
            deleteData();
            viewAll();
        }

        public void AddData(){
            btnAddData.setOnClickListener(new View.OnClickListener() {
	       @Override
	       public void onClick(View v) {
                 boolean isInserted=myDb.insertData(editName.getText().toString(), editSurname.getText().toString(),
        	                        editMarks.getText().toString());
                 if(isInserted == true)
                         Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                 else
                     Toast.makeText(MainActivity.this,"Data could not be Inserted",Toast.LENGTH_LONG).show();
             }
	   });
        }

        public void getData() {
            btngetData.setOnClickListener(new View.OnClickListener() {
	       @Override
	       public void onClick(View v) {
                 String id = editTextId.getText().toString();

                 if(id.equals(String.valueOf(""))){
                         editTextId.setError("Enter id to get data");
                         return;
                     }
                 Cursor res = myDb.getData(id);
             String data = null;
             if (res.moveToFirst()) {

                     data = "Id:"+res.getString(0)+"\n"+
       	                            "Name :"+ res.getString(1)+"\n\n"+
       	                            "Surname :"+ res.getString(2)+"\n\n"+
       	                            "Marks :"+ res.getString(3)+"\n\n";
                 }
             showMessage("Data", data);
                    }
	   });
        }

        public void viewAll(){
            btnviewAll.setOnClickListener(new View.OnClickListener() {
	       @Override
	       public void onClick(View v) {
                    Cursor res=myDb.getAllData();
                    if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }
                    StringBuffer buffer=new StringBuffer();
                    while(res.moveToNext()){
                            buffer.append("Id:"+res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n\n");
                            buffer.append("Surname :"+ res.getString(2)+"\n\n");
                            buffer.append("Marks :"+ res.getString(3)+"\n\n");
                        }
                    showMessage("Data",buffer.toString());
                }
	   });
        }

        public void updateData(){
            btnUpdate.setOnClickListener(
                 new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
                         boolean isUpdate = myDb.updateData(editTextId.getText().toString(),
        	                                editName.getText().toString(),
        	                                editSurname.getText().toString(),editMarks.getText().toString());
                         if(isUpdate == true)
                                 Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                         else
                             Toast.makeText(MainActivity.this,"Data could not be Updated",Toast.LENGTH_LONG).show();
                     }
	           }
            );
        }

        public void deleteData() {
            btnDelete.setOnClickListener(
                    new View.OnClickListener() {
	       @Override
	       public void onClick(View v) {
                            Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                            if(deletedRows > 0)
       	                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this,"Data could not be Deleted",Toast.LENGTH_LONG).show();
                        }
	           }
            );
        }

        private void showMessage(String title, String message) {
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.create();
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.show();
        }
	}
