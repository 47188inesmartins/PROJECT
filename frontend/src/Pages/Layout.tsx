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
import {
    MDBDropdown,
    MDBDropdownMenu,
    MDBDropdownToggle,
    MDBDropdownItem,
    MDBListGroup,
    MDBListGroupItem
} from 'mdb-react-ui-kit';
import 'bootstrap/dist/css/bootstrap.css';
import Dropdown from 'react-bootstrap/Dropdown';
import "../Style/LayoutCompany.css"
import {Navigate} from "react-router";
export function Layout() {
    const [isLogout, setIsLogout] = useState<boolean>(false)
    const check = React.useContext(LoggedInContextCookie).loggedInState.auth
    console.log("Check", check)

    useEffect(() => {
    }, [check]);


    function handleLogout(){
        setIsLogout(true)
    }

    function FetchLogout(){
        Fetch("/user/logout", 'POST')
        setIsLogout(false)
        window.location.href = "/"
        return <></>
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


export function LayoutRole(){
    const check = React.useContext(LoggedInContextCookie).loggedInState.role
    console.log("CHECK",check)
    const role = (check !== "client" && check !== "guest")

    return (
        <div
            style={{
                alignContent : 'right',
                display: "flex",
                height: "100vh",
                overflow: "scroll initial",
                justifyContent: "flex-end",
                right: '0'
            }}
        >
            {role ?
                <LayoutCompany/> :
                <></>
            }
        </div>
    );
}

function LayoutCompany() {
    const [manager,SetManager] = useState(false)

    const handleClickManager = () =>{
        SetManager(true)
    }

    return (
        <div>
            {manager?
                <MyCompany role={"MANAGER"}/>
                    :
                <div>
                        <nav className="menu">
                            <input type="checkbox"  className="menu-open" name="menu-open" id="menu-open"/>
                            <label className="menu-open-button" htmlFor="menu-open">
                                <span className="lines line-1"></span>
                                <span className="lines line-2"></span>
                                <span className="lines line-3"></span>
                            </label>
                            <button className="menu-item blue" onClick={handleClickManager}> <i className="fa fa-anchor"></i> </button>
                            <button className="menu-item blue menu-item-left" onClick={handleClickManager}> <i className="fa fa-anchor"></i> </button>
                        </nav>
                </div>
            }
        </div>
    );
}

function MyCompany(props:{role:String}) {
    const resp = Fetch(`/company/info?role=${props.role}`, 'GET')

    console.log("companies",resp.response)
    if(resp.response){
        if(resp.response.length === 0){
            window.location.href = ``
        }
        if(resp.response.length === 1){
            const id = resp.response[0].id
            window.location.href = `/company/${id}/managing`
        }
        if(resp.response.length > 1){
            const id = resp.response[0].id
            window.location.href = `/company/managing`
        }

    }
    return(
        <>
            {!resp.response?
                <div className="loading">
                        <div className="spinner-border" role="status">
                    <span className="sr-only">Loading...</span>
                    </div>
                </div>
                :
               <></>
            }
        </>
    );
}




function MyDropdownButton(props:{role:String,titleButton:String}) {
    const [items, setItems] = useState([]);
    const [managerCompanies,setManagerCompanies] = useState(false)

    const handleButtonClick = () => {
        setManagerCompanies(true)
    };

    useEffect(() => {
    }, [managerCompanies])

    const response = Fetch(`/company/info?role=${props.role}`,'GET')

    console.log("Layout",response)
    return (
        <div style={{ display: 'flex', justifyContent: 'center' }}>
            <Dropdown>
                <Dropdown.Toggle style={{background :'#8faacf', borderColor: '#8faacf'}} variant="success">
                    {props.titleButton}
                </Dropdown.Toggle >
                <Dropdown.Menu>
                    {response.response ?
                        <div>
                            {response.response.map((object: any) => (
                                <Dropdown.Item style={{ color: 'black' }} href="#" id={object.id}>
                                    {object.name}
                                </Dropdown.Item>))}
                        </div>
                        : <Dropdown.Item disabled>No companies</Dropdown.Item>
                    }
                </Dropdown.Menu>
            </Dropdown>
        </div>
    )

}

