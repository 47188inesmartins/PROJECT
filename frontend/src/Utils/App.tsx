import * as React from 'react';
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import {Home} from "../Pages/Home";
import {AuthnContainer} from "../Authentication/Authn";


const router = createBrowserRouter([{
    "path": "/",
    "element": <AuthnContainer><Outlet/></AuthnContainer>,
    "children": [
        {
            "path": "/",
            "element": <Home/>
        },
    ]}])


function ScheduleItApp(){
    return (
        <RouterProvider router={router} />
    )
}
export default ScheduleItApp;