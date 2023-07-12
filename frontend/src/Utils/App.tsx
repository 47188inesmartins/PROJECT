import * as React from 'react';
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import {Home} from "../Pages/Home";
import {AuthnContainer} from "../Authentication/Authn";
import {Login} from "../Pages/Login";
import {Company} from "../Pages/Company";
import {MyAppointments} from "../Pages/MyAppointments";
import {CreatingCompany} from "../Pages/CreatingCompany";
import {Layout} from "../Pages/Layout";
import {AddingEmployees} from "../Pages/Managing/AddingEmployees";
import {ProfilePage} from "../Pages/UserProfile";
import {AddingVacations} from "../Pages/Managing/AddingVacations";
import {CreatingServices} from "../Pages/CreatingServices";
import {CreatingSchedule} from "../Pages/CreatingSchedule";
import {Signup} from "../Pages/Signup";
import {ProfitCompany} from "../Pages/ProfitCompany";
import UploadPhoto from "../Pages/UploadPhoto";
import {Search} from "../Pages/Search";
import {CompanyProfileManaging} from "../Pages/CompanyProfileManaging";
import {EditServiceSchedule} from "../Pages/EditServiceSchedule";
import {UploadProfilePicture} from "../Pages/UploadProfilePicture";
import {Logout} from "../Pages/Logout";
import {MyCompanies} from "../Pages/MyCompanies";
import {CompanyProfileEmploying} from "../Pages/CompanyProfileEmploying";
import {ManagingEmployees} from "../Pages/Managing/ManagingEmployees";
import {ManagingVacations} from "../Pages/Managing/ManagingVacations";
import {Edit} from "../Pages/Managing/Edit";
import MyCalendar from "../Pages/AdvancedCalendar";


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
            "element": <CompanyProfileManaging />
        },
        {
            "path": "/company/:id/employing",
            "element": <CompanyProfileEmploying />
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
        {
            "path": "/company/:id/profile",
            "element": <CompanyProfileManaging/>
        },
        {
            "path": "/service/:id/schedule",
            "element": <EditServiceSchedule/>
        },
        {
            "path": "/company/profits",
            "element": <ProfitCompany/>
        },
        {
            "path": "/company/:id/upload-file",
            "element": <UploadPhoto/>
        },
        {
            "path": "/user/:id/upload-pic",
            "element": <UploadProfilePicture/>
        },
        {
            "path": "/logout",
            "element": <Logout/>
        },
        {
            "path": "/my/companies",
            "element": <MyCompanies/>
        },
        {
            "path": "/company/:id/managing/employees",
            "element": <ManagingEmployees/>
        },
        {
            "path": "/company/:id/managing/vacations",
            "element": <ManagingVacations/>
        },
        {
            "path": "/edit",
            "element": <Edit/>
        }
    ]
}])


function ScheduleItApp(){
    return (
        <RouterProvider router={router} />
    )
}
export default ScheduleItApp;