import * as React from 'react';
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import {Home} from "../Pages/Home";
import {AuthnContainer} from "../Authentication/Authn";
import {Login} from "../Pages/Login";
import {Company} from "../Pages/Company";
import {MyAppointments} from "../Pages/MyAppointments";
import {CreatingCompany} from "../Pages/CreatingCompany";
import {Layout} from "../Pages/Layout";
import {AddingEmployees} from "../Pages/AddingEmployees";
import {MyCompany} from "../Pages/ManagerCompany";
import {ProfilePage} from "../Pages/UserProfile";
import {AddingVacations} from "../Pages/AddingVacations";
import {CreatingServices} from "../Pages/CreatingServices";
import {CreatingSchedule} from "../Pages/CreatingSchedule";
import {Signup} from "../Pages/Signup";
import {ProfitCompany} from "../Pages/ProfitCompany";
import UploadPhoto from "../uploadPhoto";


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
            "path": "/user/appointments",
            "element": <MyAppointments/>
        },
        {
            "path": "/new/company",
            "element": <CreatingCompany/>
        },
        {
            "element": <Layout/>
        },
        {
            "path": "company/:id/employees",
            "element": <AddingEmployees />
        },
        {
            "path": "/company/:id/managing",
            "element": <MyCompany />
        },
        {
            "path": "/company/:id/vacation",
            "element": <AddingVacations />
        },
        {
            "path": "/user/profile",
            "element": <ProfilePage/>
        },
        {
            "path": "/company/:id/services",
            "element": <CreatingServices/>
        },
        {
            "path": "/company/:id/schedule",
            "element": <CreatingSchedule/>
        },
        {
            "path": "/signup",
            "element": <Signup/>
        },
        {
            "path": "/search",
            "element": <Search/>
        },
       /* {
            "path": "/company/profits",
            "element": <ProfitCompany/>
        },
        {
            "path": "/company/pic",
            "element": <UploadPhoto/>
        }
    ]
}])


function ScheduleItApp(){
    return (
        <RouterProvider router={router} />
    )
}
export default ScheduleItApp;