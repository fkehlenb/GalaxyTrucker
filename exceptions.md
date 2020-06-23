# Exceptions and Solutions

## Persistence:

### Exception: java.lang.ClassCastException 
* stack trace: class org.hibernate.mapping.SingleTableSubclass cannot be cast to class org.hibernate.mapping.RootClass
* Possible Solution: Remove @Id Tags from Subclasses.

### Exception: Unmapped class
* Solution: Add class to persistence mapping in META-INF/persistence.xml

### Exception: Primary key violation
* Solution: an entity with this key already exists you idiot, change it!
