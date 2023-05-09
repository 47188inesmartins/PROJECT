import React from 'react';
import './App.css';
import './style.css';
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import {Login} from "./pages/Login";
import {Home} from "./pages/Home";
import {Company} from "./pages/Company";


const router = createBrowserRouter([
    {
        "path": "/",
        "element": <Home/>
    },
    {
        "path": "/login",
        "element": <Login/>
    },
    {
        "path": "/company/:id",
        "element": <Company/>
    }
])


function ScheduleItApp(){
    return (
        <RouterProvider router={router} />
    )
}
export default ScheduleItApp;

