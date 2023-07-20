import * as React from 'react';
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import {AuthnContainer} from "../views/Authentication/Authn";
import {Home} from "../Pages/Home";
import {Login} from "../Pages/Login";
import {Company} from "../Pages/Company";
import {MyAppointments} from "../views/client/MyAppointments";
import {CreatingCompany} from "../Pages/CreatingCompany";
import {Layout} from "../Pages/Layout";
import {AddingEmployees} from "../views/manage/AddingEmployees";
import {CompanyProfileManaging} from "../views/manage/CompanyProfileManaging";
import {AddingVacations} from "../views/manage/AddingVacations";
import {ProfilePage} from "../views/client/UserProfile";
import {Signup} from "../Pages/Signup";
import {EditServiceSchedule} from "../Pages/EditServiceSchedule";
import {ProfitCompany} from "../Pages/ProfitCompany";
import UploadPhoto from "../Pages/UploadPhoto";
import {UploadProfilePicture} from "../Pages/UploadProfilePicture";
import {Logout} from "../Pages/Logout";
import {MyCompanies} from "../views/manage/MyCompanies";
import {ManagingEmployees} from "../views/manage/ManagingEmployees";
import {ManagingVacations} from "../views/manage/ManagingVacations";
import {AddingUnavailability} from "../views/manage/AddUnavailability";
import {CreatingServicess} from "../views/manage/CreatingServicess";
import {ManagingUnavailabilities} from "../views/manage/ManageUnavailabilities";
import {Validate} from "./Validate";



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
            "path": "/company/:id/vacation",
            "element": <AddingVacations />
        },
        {
            "path": "/user/profile",
            "element": <ProfilePage/>
        },
        {
            "path": "/signup",
            "element": <Signup/>
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
            "path": "/company/:id/managing/unavailability",
            "element": <AddingUnavailability/>
        },
        {
            "path": "/company/:id/services",
            "element": <CreatingServicess/>
        },
        {
            "path": "/company/:id/managing/unavailabilities",
            "element": <ManagingUnavailabilities/>
        },
        {
            "path": "/validate",
            "element": <Validate/>
        },
    ]
}])


function ScheduleItApp(){
    return (
        <RouterProvider router={router} />
    )
}
export default ScheduleItApp;