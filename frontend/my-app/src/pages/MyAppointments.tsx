import React, {useState} from "react";
import {MDBBtn, MDBCheckbox, MDBCol, MDBContainer, MDBInput, MDBRow} from "mdb-react-ui-kit";
import {useParams} from "react-router-dom";
import {Fetch} from "../useFetch";

export function MyAppointments() {
    const params = useParams()
    const id = params.id
    const appointments = Fetch(`/user/${id}/appointments`,"GET")
    return (
        <a>{id}</a>
    );
}