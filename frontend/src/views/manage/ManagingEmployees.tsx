import React, {useEffect, useState} from "react";
import { Link, useParams } from "react-router-dom";
import { Fetch } from "../../Utils/useFetch";
import "../../Style/ManagingEmployees.css";
import Cookies from 'js-cookie';
import {AccessDenied} from "../../Components/AccessDenied";
import {Layout, LayoutRight} from "../../Pages/Layout";
import {UsersService} from "../../Service/UsersService";





export function ManagingEmployees() {
    const cid = useParams().id;
    const response = Fetch(`company/${cid}/employees`, "GET");
    console.log("Employees", response);

    const handleDelete = (employeeId) => {
        UsersService.deleteEmployee(cid,employeeId)
    };

    function handleAddClick(){
        window.location.href = `/company/${cid}/employees`
    }

    const divElement = <div className="d-flex justify-content-center align-items-start" style={{ minHeight: '100vh' }}>
        <div className="row">
            <div className="col-md-8 offset-md-2">
                <div className="managing-employees-container justify-content-center mt-5 border-left border-right">
                    <div className="d-flex justify-content-center pt-3 pb-2">
                        <button onClick={() => handleAddClick()} type="button" className="btn btn-secondary addtxt" style={{color:'black',fontSize: '20px', fontFamily: 'Arial' }}>
                            + Add employees
                        </button>
                    </div>
                    {!response.response ? (
                        <div>...loading...</div>
                    ) : (
                        <div>
                            {response.response.map((object: any) => (
                                <div className="d-flex justify-content-center py-2" key={object.id}>
                                    <div className="second py-2 px-2">
                                        <div className="d-flex justify-content-between py-1 pt-2">
                                            <div>
                                                <span className="text2" style={{ fontSize: '20px', fontFamily: 'Arial' }}>{object.name}</span>
                                            </div>
                                            <div>
                                                <button className="btn btn-danger" onClick={() => handleDelete(object.id)}>
                                                    Delete
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            </div>
        </div>
    </div>
    return <div>
        <div className="sidebar-left">
            <Layout />
        </div>
        <AccessDenied  company={cid} content={divElement} role={['MANAGER']}/>
        <LayoutRight/>
    </div>
}