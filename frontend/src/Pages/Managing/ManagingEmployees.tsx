import React, {useEffect, useState} from "react";
import { Link, useParams } from "react-router-dom";
import { Fetch } from "../../Utils/useFetch";
import "../../Style/ManagingEmployees.css";
import {LoggedInContextCookie} from "../../Authentication/Authn";
import {Navigate} from "react-router";

export function ManagingEmployees() {
    const cid = useParams().id;
    const token =  React.useContext(LoggedInContextCookie).loggedInState.token
    const [redirect, setRedirect] = useState(false)
    const response = Fetch(`company/${cid}/employees`, "GET");
    console.log("Employees", response);

    const handleDelete = (employeeId) => {
        console.log("handledelete", employeeId)
        fetch(`/api/company/${cid}/employees?id=${employeeId}`,{
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                setRedirect(true);
            })
            .catch(error => {
                console.error('Ocorreu um erro:', error);
            });
    };

    useEffect(() => {
        if (redirect) {
            setRedirect(false);
        }
    }, [redirect]);

    if (redirect) {
        alert("Employee has been removed from your company")
        window.location.href = `/company/${cid}/managing/employees`;
    }

    const handleProfit = (employeeId) => {
        // Lógica para realizar ação de profit para o funcionário com o ID `employeeId`
    };

    return (
        <div className="managing-employees-container justify-content-center mt-5 border-left border-right">
            <div className="d-flex justify-content-center pt-3 pb-2">
                <button type="button" className="btn btn-secondary addtxt">
                    + Add employees
                </button>
            </div>
            {!response.response ? (
                <>loading</>
            ) : (
                <>
                    {response.response.map((object: any) => (
                        <div className="d-flex justify-content-center py-2" key={object.id}>
                            <div className="second py-2 px-2">
                                <div className="d-flex justify-content-between py-1 pt-2">
                                    <div>
                                        <img src="https://i.imgur.com/AgAC1Is.jpg" width="18" alt="Profile" />
                                        <span className="text2">{object.name}</span>
                                    </div>
                                    <div>
                                        <button className="btn btn-primary mr-2" onClick={() => handleProfit(object.id)}>
                                            Profit
                                        </button>
                                        <button className="btn btn-danger" onClick={() => handleDelete(object.id)}>
                                            Delete
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))}
                </>
            )}
        </div>
    );
}
