import React from 'react';
import './App.css';
import {Fetch} from "./useFetch";
import './style.css';
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import {Login} from "./pages/Login";
import {Home} from "./pages/Home";


const router = createBrowserRouter([
    {
        "path": "/",
        "element": <Home/>
    },
    {
        "path": "/login",
        "element": <Login/>
    }
])


function ScheduleItApp(){
    return (
        <RouterProvider router={router} />
    )
}
export default ScheduleItApp;

