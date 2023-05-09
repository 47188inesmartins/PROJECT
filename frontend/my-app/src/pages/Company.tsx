import {Fetch} from "../useFetch";
import React, {useEffect, useState} from "react";
import {Layout} from "../Layout";
import {
    MDBCard,
    MDBContainer,
    MDBCol,
    MDBIcon,
    MDBRipple,
    MDBRow,
} from "mdb-react-ui-kit";
import {useParams} from "react-router-dom";

export function Company() {

   // const [company, setCompany] = useState<object|undefined>(undefined)

    const params = useParams()
    console.log("url",window.location.pathname)
    console.log(params)
    const id = params.id
    console.log(id)

    const company = Fetch(`/company/${id}`, "GET");
    console.log("company",company)
    const services = Fetch(`/company/${id}/services`, "GET");
    console.log("services",services)
    return (
        <div style={{ display: "flex" }}>
            {!company.response?
              <a>Loading...</a>:
                <div>
                    <a>{company.response.description}</a>
                    {services.response.map((object: any) => (
                            <a>object.serviceName</a>
                    ))}
                </div>
            }
        </div>
    );
}