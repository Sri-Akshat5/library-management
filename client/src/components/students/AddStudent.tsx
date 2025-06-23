import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createStudent } from '../../api/studentApi';
import type { StudentDto } from '../../types/student';

const AddStudent: React.FC = () => {
  const [student, setStudent] = useState<StudentDto>({
    firstName: '',
    lastName: '',
    email: '',
  });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setStudent({
      ...student,
      [name]: value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const response = await createStudent(student);
    if (response.data) {
      navigate('/students');
    }
    if (response.error) setError(response.error);
  };

  return (
    <div className="max-w-lg mx-auto mt-10 bg-zinc-900 p-8 rounded shadow">
      <h2 className="text-2xl font-bold mb-6 text-white">Add Student</h2>

      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="mb-4">
          <label className="block text-gray-50">First Name</label>
          <input
            type="text"
            name="firstName"
            value={student.firstName}
            onChange={handleChange}
            className="w-full mt-1 p-2 border rounded bg-zinc-600 text-white border-zinc-900"
            required
          />
        </div>

        <div className="mb-4">
          <label className="block text-gray-50">Last Name</label>
          <input
            type="text"
            name="lastName"
            value={student.lastName}
            onChange={handleChange}
            className="w-full mt-1 p-2 border rounded bg-zinc-600 text-white border-zinc-900"
            required
          />
        </div>

        <div className="mb-6">
          <label className="block text-gray-50">Email</label>
          <input
            type="email"
            name="email"
            value={student.email}
            onChange={handleChange}
            className="w-full mt-1 p-2 border rounded bg-zinc-600 text-white border-zinc-900"
            required
          />
        </div>

        <div className="flex items-center">
          <button
            type="submit"
            className="bg-zinc-600 text-white px-4 py-2 rounded hover:bg-zinc-500 focus:outline-none focus:ring-2 focus:ring-zinc-500 focus:ring-offset-2"
          >
            Save
          </button>
          <button
            type="button"
            onClick={() => navigate('/students')}
            className="ml-4 bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2"
          >
            Cancel
          </button>
        </div>
      </form>

      {error && <p className="mt-4 text-red-500">{error}</p>}
    </div>
  );
};

export default AddStudent;