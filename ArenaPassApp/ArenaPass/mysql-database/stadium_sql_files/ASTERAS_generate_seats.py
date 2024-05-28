import os

def generate_sql(stadium_id, sections, rows, columns, output_dir):
    sql_statements = []
    for section, gate in sections.items():
        seat_section = gate
        for row in rows:
            for column in columns[section]:
                seat_number = f"{row}{column}"
                seat_side = 'HT' if section in [1, 2, 3] else 'AT'
                sql = f"INSERT INTO seats (seat_stadium_id, seat_section, seat_number, seat_side) VALUES ({stadium_id}, '{seat_section}', '{seat_number}', '{seat_side}');"
                sql_statements.append(sql)
    
    # Ensure the output directory exists
    os.makedirs(output_dir, exist_ok=True)
    
    output_file = os.path.join(output_dir, f'stadium_{stadium_id}_seats_inserts.sql')
    with open(output_file, 'w') as file:
        for statement in sql_statements:
            file.write(statement + '\n')
    
    print(f"SQL commands for Stadium {stadium_id} have been written to {output_file}")

# Define parameters for the second stadium
stadium_id = 103
sections = {1: 'A', 2: 'B', 3: 'C', 4: 'D'}  # 4 sections, equivalent to gates A, B, C, D
rows = [chr(i) for i in range(ord('A'), ord('S'))]  # Rows A to R (18 rows)

# Define columns for each section
columns = {
    1: range(1, 136),  # Columns 1 to 135 for sections 1 and 4
    2: range(1, 71),  # Columns 1 to 70 for section 2
    3: range(1, 71),  # Columns 1 to 70 for section 3
    4: range(1, 136)   # Columns 1 to 135 for section 4
}

output_dir = 'stadium_sql_files'
generate_sql(stadium_id, sections, rows, columns, output_dir)
