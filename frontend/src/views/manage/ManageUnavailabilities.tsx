import React, {useEffect, useState} from "react";
import { Link, useParams } from "react-router-dom";
import { Fetch } from "../../Utils/useFetch";
import "../../Style/ManagingEmployees.css";
import Cookies from 'js-cookie';
import {AccessDenied} from "../../Components/AccessDenied";
import {Layout, LayoutRight} from "../../Pages/Layout";
import {UsersService} from "../../Service/UsersService";


export function ManagingUnavailabilities() {

    const cid = useParams().id;
    const response = Fetch(`/unavailability/company/${cid}`, "GET");

    const handleDelete = (employeeId) => {
        UsersService.deleteEmployee(cid,employeeId)
    };

    const handleProfit = (employeeId) => {
        UsersService.profitEmployee(cid,employeeId)
    };

    function handleAddClick(){
        window.location.href = `/company/${cid}/employees`
    }

    const divElement = <div className="d-flex justify-content-center align-items-start" style={{ minHeight: '100vh' }}>
        <div className="row">
            <div className="col-md-8 offset-md-2">
                <div className="managing-employees-container justify-content-center mt-5 border-left border-right">

                    {!response.response ? (
                        <div>...loading...</div>
                    ) : (
                        <div>
                            <h2 className="fw-bold mb-2 text-uppercase">
                                Unavailabilities
                            </h2>
                            {response.response.map((object: any) => (
                                <div className="d-flex justify-content-center py-2" key={object.id}>
                                    <div className="second py-2 px-2">
                                        <div className="d-flex justify-content-between py-1 pt-2">
                                            <p>{object.hourBegin}-{object.hourEnd}</p>
                                            <p>User:{object.user}</p>
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
