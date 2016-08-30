# AddressBookgrio

This is my address book application for grio. I will explain the classes and their use below

AddNewContact extends Activity - activity page that adds a new contact to the database and returns to MainActivity

Contact - An object class that stores information.

ContactListAdapter- Class designed for layout, used to show the contacts in a list in MainActivity

DatabaseAsyncMethod Extends AsyncTask - This class to make sure every call to the database uses AsyncTask

DbBitMapUtility - Used for storing Images into database. Turns BitMap into byte[] array to store data. Turns byte[] array into bitmap to show

EditContact extends Activity - activity page that allows editing of a contact and storing into database.

MainActivity - activity page that shows a list of contacts, and allows search by first name.
