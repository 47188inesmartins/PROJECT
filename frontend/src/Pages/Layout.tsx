import * as React from 'react';
import {
    CDBSidebar,
    CDBSidebarContent,
    CDBSidebarFooter,
    CDBSidebarHeader,
    CDBSidebarMenu,
    CDBSidebarMenuItem
}from "cdbreact";
import { NavLink } from "react-router-dom";
import {LoggedInContextCookie} from "../Authentication/Authn";
import {useEffect, useState} from "react";
import {Fetch} from "../Utils/useFetch";
import 'bootstrap/dist/css/bootstrap.css';
import "../Style/LayoutCompany.css"

export function Layout() {
    const [isLogout, setIsLogout] = useState<boolean>(false)
    const auth = React.useContext(LoggedInContextCookie).loggedInState.auth
    console.log("Check", auth)

    useEffect(() => {
    }, [auth,isLogout]);


    function handleLogout(){
        setIsLogout(true)
    }

    function FetchLogout(){
        const resp = Fetch("/user/logout", 'POST').response

        if(!resp) {
            return(<p>...loading...</p>);
        }

        if(resp.status) {
            window.location.href = `/`
            return(<></>);
        }

        if(resp){
            setIsLogout(false)
            window.location.href = '/'
            return(
                <></>
            )
        }
    }

    return (
        <div>
            {!isLogout ?
                <div
                    style={{display: "flex", height: "100vh", overflow: "scroll initial"}}
                >
                    <CDBSidebar textColor="#fff" backgroundColor="black" className={""} breakpoint={0} toggled={false}
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
                        {auth ?
                            <div style={{ position: 'relative', minHeight: '100vh' }}>
                                <CDBSidebarContent className="sidebar-content">
                                    <CDBSidebarMenu>
                                        <NavLink to="/user/appointments">
                                            <CDBSidebarMenuItem icon="columns">Appointments</CDBSidebarMenuItem>
                                        </NavLink>
                                        <NavLink to="/new/company">
                                            <CDBSidebarMenuItem icon="table">Company</CDBSidebarMenuItem>
                                        </NavLink>
                                        <NavLink to="/user/profile">
                                            <CDBSidebarMenuItem icon="user">Profile</CDBSidebarMenuItem>
                                        </NavLink>
                                    </CDBSidebarMenu>
                                </CDBSidebarContent>
                                    <div style={{ position: 'absolute', bottom: '0', width: '100%' }}>
                                        <NavLink to="/">
                                            <CDBSidebarMenuItem><button onClick={handleLogout}> Logout </button></CDBSidebarMenuItem>
                                        </NavLink>
                                    </div>
                            </div>
                            :
                            <div>
                                <CDBSidebarContent className="sidebar-content">
                                    <CDBSidebarMenu>
                                        <NavLink to="/login">
                                            <CDBSidebarMenuItem icon="columns">Appointments</CDBSidebarMenuItem>
                                        </NavLink>
                                        <NavLink to="/new/company">
                                            <CDBSidebarMenuItem icon="table">Company</CDBSidebarMenuItem>
                                        </NavLink>
                                        <NavLink to="/login">
                                            <CDBSidebarMenuItem icon="user">Profile</CDBSidebarMenuItem>
                                        </NavLink>
                                    </CDBSidebarMenu>
                                </CDBSidebarContent>
                                <div style={{ position: 'absolute', bottom: '0', width: '100%' }}>
                                    <CDBSidebarMenu>
                                        <NavLink to="/login">
                                            <CDBSidebarMenuItem>Login</CDBSidebarMenuItem>
                                        </NavLink>
                                    </CDBSidebarMenu>
                                </div>
                            </div>
                            }
                    </CDBSidebar>
                </div>
                : <FetchLogout/>
            }
        </div>
    );
}