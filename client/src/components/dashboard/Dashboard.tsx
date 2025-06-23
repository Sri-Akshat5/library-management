import React, { useState, useEffect } from 'react';
import { getDashboardData } from '../../api/dashboardApi';
import type { DashboardData } from '../../types/api';
import { FaChartLine } from "react-icons/fa6";


const Dashboard: React.FC = () => {
  const [data, setData] = useState<DashboardData>({
    bookCount: 0,
    studentCount: 0,
    borrowedCount: 0,
  });
  const [error, setError] = useState('');

 useEffect(() => {
  const fetchData = async () => {
    const response = await getDashboardData();
    if (response.success && response.data) {
      setData(response.data);
    } else {
      setError(response.error || 'Failed to load dashboard data.');
    }
  };
  fetchData();
}, []);


  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex items-center mb-8">
        <div className="bg-zinc-600 p-3 rounded-lg shadow-md">
          <FaChartLine className='text-zinc-900 text-2xl'/>
        </div>
        <h2 className="ml-4 text-3xl font-bold text-zinc-800">Dashboard</h2>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-zinc-900 shadow-lg rounded-xl p-6 text-center border border-zinc-200 hover:shadow-xl transition">
          <p className="text-3xl font-semibold text-yellow-300">{data.bookCount}</p>
          <p className="mt-2 text-white font-medium">Total Books</p>
        </div>

        <div className="bg-zinc-900 shadow-lg rounded-xl p-6 text-center border border-zinc-200 hover:shadow-xl transition">
          <p className="text-3xl font-semibold text-green-600">{data.studentCount}</p>
          <p className="mt-2 text-white font-medium">Total Students</p>
        </div>

        <div className="bg-zinc-900 shadow-lg rounded-xl p-6 text-center border border-zinc-200 hover:shadow-xl transition">
          <p className="text-3xl font-semibold text-red-600">{data.borrowedCount}</p>
          <p className="mt-2 text-white font-medium">Books Currently Borrowed</p>
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
    </div>
  );
};

export default Dashboard;