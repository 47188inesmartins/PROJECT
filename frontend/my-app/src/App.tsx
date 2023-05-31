import React from 'react';
import './App.css';
import './style.css';
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import {Login} from "./pages/Login";
import {Home} from "./pages/Home";
import {Company} from "./pages/Company";
import {MyAppointments} from "./pages/MyAppointments";
import {CreatingCompany} from "./pages/CreatingCompany";
import {AuthnContainer} from "./pages/Authn";


const router = createBrowserRouter([{
    "path": "/",
    "element": <AuthnContainer><Outlet/></AuthnContainer>,
    "children": [
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
    },
    {
        "path": "/user/:id/appointments",
        "element": <MyAppointments/>
    },
    {
        "path": "/new/company",
        "element": <CreatingCompany/>
    }
]}])


function ScheduleItApp(){
    return (
        <RouterProvider router={router} />
    )
}
export default ScheduleItApp;

