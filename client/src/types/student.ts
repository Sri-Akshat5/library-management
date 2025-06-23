export interface Student {
  studentId: number;
  firstName: string;
  lastName: string;
  email: string;
}

export interface StudentDto extends Omit<Student, 'studentId'> {
  studentId?: number;
}