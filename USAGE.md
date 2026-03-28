# Campus Course Records Manager - Usage Guide

## Sample Commands

### Student Management
- Add student: Use menu option 1 → 2
- View profile: Use menu option 1 → 4
- List all students: Use menu option 1 → 1

### Course Management  
- Search courses: Use menu option 2 → 2
- Add course: Use menu option 2 → 3

### Enrollment & Grading
- Enroll student: Use menu option 3
- Record grades: Use menu option 4

### File Operations
- Export data: Use menu option 5
- Create backup: Use menu option 6 → 1
- Recursive size check: Use menu option 6 → 2

### Sample Data Files
Place CSV files in `data/` folder:
- `students.csv`: id,regNo,fullName,email,status
- `courses.csv`: code,title,credits,instructor,semester,department,active

## Running the Application
```bash
cd src
javac edu/ccrm/**/*.java
java edu.ccrm.cli.Main