import React, {useEffect, useState} from "react";
import { Link, useParams } from "react-router-dom";
import { Fetch } from "../../Utils/useFetch";
import "../../Style/ManagingEmployees.css";
import {LoggedInContextCookie} from "../../Authentication/Authn";
import {Navigate} from "react-router";


/*
*
*
* era bom o manager ver tambem as ferias que cada employee da sua empresa tem
secalhar o manager devia aprovar as ferias de um eployee quando um employee tenta meter umas ferias
*
*
* */



export function ManagingVacations() {
    const cid = useParams().id;
    const token =  React.useContext(LoggedInContextCookie).loggedInState.token
    const [redirect, setRedirect] = useState(false)
    const response = Fetch(`company/${cid}/vacations`, "GET");
    console.log("Employees", response);

    const handleDelete = (vacationId) => {
        console.log("handledelete", vacationId)
        fetch(`/api/company/${cid}/vacation/${vacationId}`,{
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
        alert("Vacation has been removed from your company")
        window.location.href = `/company/${cid}/managing/vacations`;
    }

    const handleProfit = (employeeId) => {
        // Lógica para realizar ação de profit para o funcionário com o ID `employeeId`
    };

    function handleAdd(){
        window.location.href = `/company/${cid}/vacation`
    }


    return (
        <div className="managing-employees-container justify-content-center mt-5 border-left border-right">
            <div className="d-flex justify-content-center pt-3 pb-2">
                <button type="button" className="btn btn-secondary addtxt" onClick={handleAdd}>
                    + Add new vacation
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
                                        <span className="text2">Begin date: {object.dateBegin}</span>
                                        <br/>
                                        <span className="text2">End date: {object.dateEnd}</span>
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
                </>
            )}
        </div>
    );
}
