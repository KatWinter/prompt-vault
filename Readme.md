## Database

```bash
# Create the database
mysql -u root -p < ./db/up.sql

# Seed the database 
mysql -u root -p < ./db/sample-data.sql

# Drop database and user
mysql -u root -p < ./db/down.sql
```

## Running the app

Admin user:
Username: admin
Password: admin

Regular user:
Username: user
Password: user
