import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getStudents, searchStudents, deleteStudent } from '../../api/studentApi';
import type { Student } from '../../types/student';
import { confirmAction } from '../../utils/helpers';
import { PiStudentFill } from "react-icons/pi";
import { FaEdit } from 'react-icons/fa';
import { RiDeleteBin6Fill } from 'react-icons/ri';


const StudentList: React.FC = () => {
  const [students, setStudents] = useState<Student[]>([]);
  const [keyword, setKeyword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
 

  useEffect(() => {
    fetchStudents();
  }, []);

  const fetchStudents = async () => {
    const response = await getStudents();
    if (response.data) setStudents(response.data);
    if (response.error) setError(response.error);
  };

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    const response = await searchStudents(keyword);
    if (response.data) setStudents(response.data);
    if (response.error) setError(response.error);
  };

  const handleDelete = async (id: number) => {
    if (confirmAction('Are you sure you want to delete this student?')) {
      const response = await deleteStudent(id);
      if (response.data) {
        setSuccess('Student deleted successfully');
        fetchStudents();
      }
      if (response.error) setError(response.error);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-8 gap-4">
        <div className="flex items-center">
          <div className="bg-zinc-700 p-3 rounded-lg shadow-md">
           <PiStudentFill className=" text-white text-2xl"/>
          </div>
          <h2 className="ml-4 text-3xl font-bold text-zinc-800">Student List</h2>
        </div>

        <div className="flex flex-col sm:flex-row gap-3 w-full md:w-auto">
          <form onSubmit={handleSearch} className="relative w-full">
            <input
              type="text"
              value={keyword}
              onChange={(e) => setKeyword(e.target.value)}
              placeholder="Search by name"
              className="w-full pl-10 pr-4 py-2 rounded-lg  border bg-zinc-700 text-white border-zinc-900 "
            />
          </form>

          <Link
            to="/students/add"
            className="bg-zinc-600 hover:bg-zinc-700 text-white font-medium py-2 px-4 rounded-lg transition duration-200 flex items-center justify-center whitespace-nowrap"
          >
             Add Student
          </Link>
        </div>
      </div>

       <div className="bg-zinc-900 rounded-xl shadow-lg overflow-hidden border border-gray-200">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-zinc-200">
            <thead className="bg-gradient-to-r from-zinc-600 to-zinc-800">
              <tr>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">ID</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">First Name</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Last Name</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Email</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
           <tbody className="bg-zinc-900 divide-y divide-zinc-200">
              {students.map((student) => (
                <tr key={student.studentId} className="hover:bg-zinc-500 transition duration-150">
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-white">{student.studentId}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{student.firstName}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{student.lastName}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{student.email}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                    <div className="flex space-x-3">
                      <Link
                        to={`/students/edit/${student.studentId}`}
                        className="text-blue-600 hover:text-blue-800 transition duration-150"
                        title="Edit"
                      >
                        <FaEdit />
                      </Link>
                      <button
                        onClick={() => handleDelete(student.studentId)}
                        className="text-red-600 hover:text-red-800 transition duration-150"
                        title="Delete"
                      >
                         <RiDeleteBin6Fill />
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {error && (
        <div className="mt-6 p-4 bg-red-50 border-l-4 border-red-500 text-red-700">
          <div className="flex items-center">
            <i className="fas fa-exclamation-circle mr-3"></i>
            <span>{error}</span>
          </div>
        </div>
      )}

      {success && (
        <div className="mt-6 p-4 bg-blue-50 border-l-4 border-blue-500 text-blue-700">
          <div className="flex items-center">
            <i className="fas fa-check-circle mr-3"></i>
            <span>{success}</span>
          </div>
        </div>
      )}
    </div>
  );
};

export default StudentList;