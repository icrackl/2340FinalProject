package todolist.wireandlights;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MainActivity extends Activity implements View.OnClickListener {
	private String chosenUser;
	private EditText loginusername, loginpassword;
	private EditText regName, regUsername, regPassword, regConfirmedPassword,
			regEmail;
	private TextView nameStatus, usernameStatus, passwordStatus,
			confirmedPasswordStatus, emailStatus;
	private ViewSwitcher switcher;
	private ArrayList<User> userlist;
	private Button login, loginregister, cancel, register;
	private TextView loginStatus;
	private int status = 1;
	private MyApp facade;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initialize();
		login.setOnClickListener(this);
		loginregister.setOnClickListener(this);
		register.setOnClickListener(this);
		cancel.setOnClickListener(this);
		facade = MyApp.getInstance();
		try {
			facade.loadBinary();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.logButton:
			if (tryLogin() == true)
				login();
			break;
		case R.id.regButton:
			switcher.showNext();
			resetTextFields();
			break;
		case R.id.register:
			register();
			break;
		case R.id.cancelReg:
			resetTextFields();
			switcher.showPrevious();
			loginStatus.setText("");
			break;
		}

	}

	/**
	 * register method check each single input field of Register class for input
	 * validation if all validation is true, it will store User's information
	 * else, display the error notification beside each input field that has
	 * invalid format.
	 */
	private void register() {
		String input, password;

		TextView[] TextViewList = { nameStatus, usernameStatus, passwordStatus,
				confirmedPasswordStatus, emailStatus };
		EditText[] EditTextList = { regName, regUsername, regPassword,
				regConfirmedPassword, regEmail };
		String[] inputTypeList = { "name", "username", "password",
				"matchPassword", "email" };
		boolean stat = false;
		password = (String) regPassword.getText().toString();
		for (int i = 0; i < TextViewList.length; i++) {
			input = (String) EditTextList[i].getText().toString();
			if (stat) {
				if (checkFormat(inputTypeList[i], input, password)) {
					TextViewList[i].setText(" ");
					stat = false;
				} else {
					TextViewList[i].setText(R.string.confirmedpw_errorMSG);
					status -= 1;
					stat = false;
				}
			} else if (checkFormat(inputTypeList[i], input, password)) {
				TextViewList[i].setText(" ");
				if (i == 2) {
					stat = true;
				}
			} else {
				switch (i) {
				case 0:
					TextViewList[i].setText(R.string.name_errorMSG);
					status -= 1;
					break;
				case 1:
					TextViewList[i].setText(R.string.username_errorMSG);
					status -= 1;
					break;
				case 2:
					TextViewList[i].setText(R.string.password_errorMSG);
					status -= 1;
					break;
				case 3:
					TextViewList[i].setText(R.string.confirmedpw_errorMSG);
					status -= 1;
					break;
				case 4:
					TextViewList[i].setText(R.string.email_errorMSG);
					status -= 1;
					break;
				}
			}
		}

		// store user information if accepted
		if (status >= 1) {
			if (facade.addUser((String) regName.getText().toString(),
					(String) regUsername.getText().toString(),
					(String) regPassword.getText().toString(),
					(String) regEmail.getText().toString())) {
				emailStatus.setText("Account Saved");
				try {
					facade.saveBinary();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//login();
			} else {
				emailStatus.setText("Account already exists");
			}
		} else {
			status = 1;
		}

	}

	private void login() {
		Intent intent = new Intent("todolist.wireandlights.TODOLIST");
		Bundle bundle = new Bundle();
		bundle.putString("chosenUser", loginusername.getText().toString());
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private boolean tryLogin() {
		if (loginusername.getText().length() > 0
				&& loginpassword.getText().length() > 0) {
			if (facade.attemptLogin(loginusername.getText().toString(),
					loginpassword.getText().toString())) {
				loginStatus.setText("GOOD LOGIN");
				return true;
			} else {
				loginStatus.setText("BAD LOGIN");
				return false;
			}
		} else {
			loginStatus.setText("BAD LOGIN");
			return false;
		}
	}

	public void resetTextFields() {
		regName.setText("");
		regUsername.setText("");
		regEmail.setText("");
		regPassword.setText("");
		regConfirmedPassword.setText("");
		loginusername.setText("");
		loginpassword.setText("");
	}

	/**
	 * initialize method initializes all the variables of this class.
	 */
	private void initialize() {
		switcher = (ViewSwitcher) findViewById(R.id.profileSwitcher);
		loginusername = (EditText) findViewById(R.id.userView);
		loginpassword = (EditText) findViewById(R.id.passView);
		regName = (EditText) findViewById(R.id.evRegName);
		regUsername = (EditText) findViewById(R.id.evUserName);
		regEmail = (EditText) findViewById(R.id.evEmail);
		regPassword = (EditText) findViewById(R.id.evPassword);
		regConfirmedPassword = (EditText) findViewById(R.id.evConfirmPass);
		login = (Button) findViewById(R.id.logButton);
		loginregister = (Button) findViewById(R.id.regButton);
		register = (Button) findViewById(R.id.register);
		cancel = (Button) findViewById(R.id.cancelReg);
		userlist = new ArrayList<User>();
		loginStatus = (TextView) findViewById(R.id.logView);
		nameStatus = (TextView) findViewById(R.id.tvRegNameSTATUS);
		usernameStatus = (TextView) findViewById(R.id.tvUserNameSTATUS);
		passwordStatus = (TextView) findViewById(R.id.tvPasswordSTATUS);
		confirmedPasswordStatus = (TextView) findViewById(R.id.tvConfirmPasswordSTATUS);
		emailStatus = (TextView) findViewById(R.id.tvEmailSTATUS);
	}

	/**
	 * checkFormat method tests for valid input
	 * 
	 * @param type
	 *            must be one of the following format
	 *            ("name","username","password","email" or "matchPassword")
	 * @param str
	 *            str is a string to be tested
	 * @param str2
	 *            use this string as confirmedpassword, else just pass in empty
	 *            string "".
	 * @return return true if input meets requirements.
	 */
	public boolean checkFormat(String type, String str, String STR) {
		if (type.equalsIgnoreCase("name")) {
			// must contain first and last name
			return str.matches("[A-Za-z\\-]* [A-Za-z\\-]*");
		} else if (type.equalsIgnoreCase("username")) {
			// must contains at least 1 uppercase and digit. length from 4-16
			return str.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})");
		} else if (type.equalsIgnoreCase("password")) {
			// must contains at least 1 uppercase and digit. length from 4-16
			return str.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16})");
		} else if (type.equalsIgnoreCase("email")) {
			// must contain symbols @ and . and ending type of email
			return str.matches("[\\w_.\\d]*@[\\w_.\\d]*\\.(com|edu|org|net)");
		} else if (type.equalsIgnoreCase("matchPassword")) {
			if (str.contentEquals(STR)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}