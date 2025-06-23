import React from 'react';
import {  Routes, Route } from 'react-router-dom';
import Layout from './components/layout/Layout';
import BookList from './components/books/BookList';
import AddBook from './components/books/AddBook';
import EditBook from './components/books/EditBook';
import StudentList from './components/students/StudentList';
import AddStudent from './components/students/AddStudent';
import EditStudent from './components/students/EditStudent';
import HistoryList from './components/history/HistoryList';
import BorrowBook from './components/history/BorrowBook';
import Dashboard from './components/dashboard/Dashboard';

const App: React.FC = () => {
  return (
   
      <Layout>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/books" element={<BookList />} />
          <Route path="/books/add" element={<AddBook />} />
          <Route path="/books/edit/:id" element={<EditBook />} />
          <Route path="/students" element={<StudentList />} />
          <Route path="/students/add" element={<AddStudent />} />
          <Route path="/students/edit/:id" element={<EditStudent />} />
          <Route path="/history" element={<HistoryList />} />
          <Route path="/history/borrow" element={<BorrowBook />} />
        </Routes>
      </Layout>
   
  );
};

export default App;