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

First create & seed the database, then run:

```bash
./mvnw spring-boot:run
``` 

Login with any user for testing:

| Username | Password | Type  |
|----------|----------|-------|
| admin    | admin    | Admin |
| user     | user     | User  |
| user2    | user2    | User  |
