import * as React from 'react';
import {
    CDBSidebar,
    CDBSidebarContent,
    CDBSidebarFooter,
    CDBSidebarHeader,
    CDBSidebarMenu,
    CDBSidebarMenuItem
} from "cdbreact";
import { NavLink } from "react-router-dom";
import {Navigate, useLocation} from "react-router";
import {LoggedInContextCookie} from "../Authentication/Authn";
import {useEffect, useState} from "react";
import {Fetch} from "../Utils/useFetch";


export function Layout() {
    const [isLogout, setIsLogout] = useState<boolean>(false)
    const check = React.useContext(LoggedInContextCookie).loggedInState.auth
    console.log("Check", check)

  //  if(isLogout)

    useEffect(() => {
    }, [check]);


    function handleLogout(){
        setIsLogout(true)
        //Fetch("/logout", 'POST')
    }

    function FetchLogout(){
        Fetch("/user/logout", 'POST')
       // setIsLogout(false)
        return <Navigate to={"/"}></Navigate>
    }

    return (
        <div>
            {!isLogout ?
                <div
                    style={{display: "flex", height: "100vh", overflow: "scroll initial"}}
                >
                    <CDBSidebar textColor="#fff" backgroundColor="#333" className={""} breakpoint={0} toggled={false}
                                minWidth={""} maxWidth={""}>
                        <CDBSidebarHeader prefix={<i className="fa fa-bars fa-large"></i>}>
                            <a
                                href="/"
                                className="text-decoration-none"
                                style={{color: "inherit"}}
                            >
                                Schedule it
                            </a>
                        </CDBSidebarHeader>

                        <CDBSidebarContent className="sidebar-content">
                            <CDBSidebarMenu>
                                <NavLink to="/user/1/appointments">
                                    <CDBSidebarMenuItem icon="columns">Appointments</CDBSidebarMenuItem>
                                </NavLink>
                                <NavLink to="/new/company">
                                    <CDBSidebarMenuItem icon="table">Company</CDBSidebarMenuItem>
                                </NavLink>
                                <NavLink to="/profile">
                                    <CDBSidebarMenuItem icon="user">Profile</CDBSidebarMenuItem>
                                </NavLink>
                            </CDBSidebarMenu>
                        </CDBSidebarContent>

                        <CDBSidebarFooter>
                            {!check ?
                                <CDBSidebarMenu>
                                    <NavLink to="/login">
                                        <CDBSidebarMenuItem>Login</CDBSidebarMenuItem>
                                    </NavLink>
                                </CDBSidebarMenu> :
                                <CDBSidebarMenu>
                                    <NavLink to="/">
                                        <CDBSidebarMenuItem onClick={handleLogout}>Logout</CDBSidebarMenuItem>
                                    </NavLink>
                                </CDBSidebarMenu>
                            }
                        </CDBSidebarFooter>
                    </CDBSidebar>
                </div>
                : <FetchLogout/>
            }
        </div>
    );
}