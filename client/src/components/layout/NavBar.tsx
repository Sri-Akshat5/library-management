import React from 'react';
import { Link } from 'react-router-dom';
import { FaBook } from "react-icons/fa";


const Navbar: React.FC = () => {
  return (
    <nav className="bg-zinc-700 text-white shadow-md">
      <div className="container mx-auto px-4 py-4 flex flex-col md:flex-row justify-between items-center">
        <div className="flex items-center gap-2">
          <FaBook className=" text-white text-xl"/>
          <i ></i>
          <h1 className="text-xl font-bold">Library System</h1>
        </div>
        <div className="mt-2 md:mt-0 space-x-4 text-sm font-medium">
          <Link to="/" className="hover:underline hover:text-zinc-200 transition">
            Dashboard
          </Link>
          <Link to="/books" className="hover:underline hover:text-zinc-200 transition">
            Books
          </Link>
          <Link to="/students" className="hover:underline hover:text-zinc-200 transition">
            Students
          </Link>
          <Link to="/history" className="hover:underline hover:text-zinc-200 transition">
            Borrow
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;