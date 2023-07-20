import * as React from 'react';
import {useParams} from "react-router-dom";
import {Fetch} from "./useFetch";

export function Validate() {

    const searchParams = new URLSearchParams(window.location.search);
    const email = searchParams.get("email");
    console.log("email",email)
    const response = fetch(`/api/user/validate?email=${email}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        }}
    ).then(data =>
        window.location.href = '/'
    ).catch(error=>
        window.location.href = '/'
    )
    return (
       <>...loading...</>
    );
}
