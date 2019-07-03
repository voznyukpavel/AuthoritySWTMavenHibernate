package pro1.pro_1;

import pojos.Person;

import util.Factory;

import java.sql.SQLException;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Point;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.GridLayout;

public class App {

	private static Person person;
	private static List<Person> persons;
	private static Integer id_person;

	private static Display display;
	private static Shell shell;
	private static GridLayout gridlayout;
	private static RowLayout rowlayout1, rowlayout2;
	private static Composite com1, com2, com3, com4;
	private static Label loginlable, passwordlable;
	private static Text logintext, passwordtext;
	private static Button showAllbutton, findByLoginbutton, addButtton, updateButtton, removeButtton, exiteButtton;
	private static Table table;

	public static void main(String[] arg) {
		initUI();
	}

	private static void initUI() {
		display = new Display();
		shell = new Shell(display);
		
		gridlayout = new GridLayout();
		gridlayout.numColumns=1;
		shell.setLayout(gridlayout);
		
		com1 = new Composite(shell, SWT.NULL);
		com2 = new Composite(shell, SWT.NULL);
		com3 = new Composite(shell, SWT.NULL);
		com4 = new Composite(shell, SWT.NULL);

		rowlayout1 = new RowLayout();
		rowlayout1.wrap = false;
		com1.setLayout(rowlayout1);
		com2.setLayout(rowlayout1);
		com4.setLayout(rowlayout1);

		rowlayout2 = new RowLayout();
		rowlayout2.marginLeft = 15;
		rowlayout2.wrap = false;
		rowlayout2.spacing = 30;
		com3.setLayout(rowlayout2);

		loginlable = new Label(com1, SWT.PUSH);
		loginlable.setText("Login:");
		loginlable.setLayoutData(new RowData(55, 20));

		logintext = new Text(com1, SWT.SINGLE | SWT.BORDER);
		logintext.setLayoutData(new RowData(200, 20));

		showAllbutton = new Button(com1, SWT.PUSH);
		showAllbutton.setText("Show all");
		showAllbutton.setLayoutData(new RowData(90, 25));
		showAllbutton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				showAll();
			}
		});

		passwordlable = new Label(com2, SWT.PUSH);
		passwordlable.setText("Password:");
		passwordlable.setLayoutData(new RowData(55, 20));

		passwordtext = new Text(com2, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
		passwordtext.setLayoutData(new RowData(200, 20));

		findByLoginbutton = new Button(com1, SWT.PUSH);
		findByLoginbutton.setText("Find by login");
		findByLoginbutton.setLayoutData(new RowData(90, 25));
		findByLoginbutton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				findByLogin();
			}
		});

		addButtton = new Button(com3, SWT.PUSH);
		addButtton.setText("Add");
		addButtton.setLayoutData(new RowData(90, 25));
		addButtton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				add();
			}
		});

		updateButtton = new Button(com3, SWT.PUSH);
		updateButtton.setText("Update");
		updateButtton.setLayoutData(new RowData(90, 25));
		updateButtton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				update();
			}
		});

		removeButtton = new Button(com3, SWT.PUSH);
		removeButtton.setText("Remove");
		removeButtton.setLayoutData(new RowData(90, 25));
		removeButtton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				remove();
			}
		});

		exiteButtton = new Button(com3, SWT.PUSH);
		exiteButtton.setText("Close");
		exiteButtton.setLayoutData(new RowData(90, 25));
		exiteButtton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				exit();
			}
		});

		table = new Table(com4, SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);
		table.setLayoutData(new RowData(450, 100));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		initTableHead(table);
		table.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				setTextsFromTable(e);
			}
		});
		showAll();

		shell.setText("Authorization");
		shell.setSize(500, 280);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private static void findByLogin() {
		if (!logintext.getText().trim().equals("")) {
			try {
				persons = Factory.getInstance().getPersonDAO().findbyLogin(logintext.getText(), true);
				displayResult(persons);
			} catch (SQLException ex) {
				Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private static void add() {
		if (!logintext.getText().trim().equals("") && !passwordtext.getText().trim().equals("")) {
			person.setLogin(logintext.getText().trim());
			person.setPassword(passwordtext.getText().trim());
			person.setIdPerson(id_person);
			person.setPuttime(null);
			try {
				Factory.getInstance().getPersonDAO().addPerson(person);
				showAll();
			} catch (SQLException ex) {
				Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			JOptionPane.showMessageDialog(null, "не введен логин или пароль", "ошибка ввода", JOptionPane.OK_OPTION);
		}

	}

	private static void update() {
		if (!logintext.getText().trim().equals("") && !passwordtext.getText().trim().equals("")) {
			person.setLogin(logintext.getText().trim());
			person.setPassword(passwordtext.getText().trim());
			person.setIdPerson(id_person);
			try {
				if (id_person == null) {
					person.setPuttime(null);
					person.setLogin(logintext.getText().trim());
					person.setPassword(passwordtext.getText().trim());
					Factory.getInstance().getPersonDAO().addPerson(person);
				}
				Factory.getInstance().getPersonDAO().updatePerson(person);
				showAll();
			} catch (SQLException ex) {
				Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {

			JOptionPane.showMessageDialog(null, "не введен логин или пароль", "ошибка ввода", JOptionPane.OK_OPTION);
		}

	}

	private static void remove() {
		if (!logintext.getText().trim().equals("")) {
			person.setLogin(logintext.getText().trim());
			try {
				persons = Factory.getInstance().getPersonDAO().findbyLogin(logintext.getText(), true);
				person.setIdPerson(persons.get(0).getIdPerson());
				Factory.getInstance().getPersonDAO().deletePerson(person);
				showAll();
			} catch (SQLException ex) {
				Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			JOptionPane.showMessageDialog(null, "не введен логин или пароль", "ошибка ввода", JOptionPane.OK_OPTION);
		}

	}

	private static void exit() {
		System.exit(0);
	}

	private static void initTableHead(Table table) {
		String tableHead[] = { "ID", "Login", "Password", "PutTime" };

		tableHead[0] = spaceAdder(tableHead[0], 4);
		tableHead[1] = spaceAdder(tableHead[1], 12);
		tableHead[2] = spaceAdder(tableHead[2], 8);
		tableHead[3] = spaceAdder(tableHead[3], 15);

		for (int i = 0; i < tableHead.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(tableHead[i]);
		}
		for (int i = 0; i < 4; i++) {
			table.getColumn(i).pack();
		}
	}

	private static String spaceAdder(String string, int count) {
		for (int i = 0; i <= count; i++) {
			string = " " + string + " ";
		}
		return string;
	}

	private static void showAll() {
		try {
			persons =Factory.getInstance().getPersonDAO().findAll();
		} catch (SQLException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
		displayResult(persons);
	}

	private static void displayResult(List<Person> resultList) {
		table.removeAll();
		for (Person o : resultList) {
			TableItem item = new TableItem(table, SWT.NONE);
			person = (Person) o;
			item.setText(new String[] { person.getIdPerson() + "", person.getLogin(), person.getPassword(),
					person.getPuttime() + "" });
		}
	}

	private static void setTextsFromTable(MouseEvent e) {
		if (e.button == 1) {
			Point pt = new Point(e.x, e.y);
			int index = 0;
			while (index < table.getItemCount()) {
				TableItem item = table.getItem(index);
				for (int i = 0; i < 4; i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)) {
						logintext.setText(item.getText(1));
						passwordtext.setText(item.getText(2));
						id_person = Integer.parseInt(item.getText(0));
					}
				}
				index++;
			}
		}
	}
}
