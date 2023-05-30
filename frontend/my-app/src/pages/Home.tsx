import {Fetch} from "../useFetch";
import React, {useEffect, useState} from "react";
import {Layout, ManagerLayout} from "../Layout";
import {
    MDBCard,
    MDBContainer,
    MDBCol,
    MDBIcon,
    MDBRipple,
    MDBRow,
} from "mdb-react-ui-kit";
import {RequireRole} from "../Authentication/RequiredAuth";

export function Home() {

    const [companies, setCompanies] = useState([]);

    const response = Fetch("/company", "GET");


    return (
        <div style={{ display: "flex" }}>
            <td>
                <div style={{position: "fixed",
                    top: 0,
                    left: 0,
                    bottom: 0,
                    width: "200px",
                    overflowY: "auto" }}>
                    <Layout />
                </div>
            </td>
            <td>
                <div className="list-container" style={{marginLeft: "200px",
                    overflowY: "auto"}}>
                    {!response.response ? (
                        <p>Loading...</p>
                    ) : (
                        <div>
                        <MDBContainer className="py-5">
                            <MDBCard className="px-3 pt-3"
                                     style={{ maxWidth: "100%"}} >
                                <div>
                                    {response.response.map((object: any) => (
                                        <a href={`/company/${object.id}`} className="text-dark">
                                            <MDBRow className="mb-4 border-bottom pb-2">
                                                <MDBCol size="3">
                                                    <img
                                                        src="https://mdbcdn.b-cdn.net/img/new/standard/city/041.webp"
                                                        className="img-fluid shadow-1-strong rounded"
                                                        alt="Hollywood Sign on The Hill"
                                                    />
                                                </MDBCol>

                                                <MDBCol size="9">
                                                    <p className="mb-2">
                                                        <strong>{object.name}</strong>
                                                    </p>
                                                    <p>
                                                        <u> {object.description}</u>
                                                    </p>
                                                </MDBCol>
                                            </MDBRow>
                                        </a>
                                    ))}

                                </div>
                            </MDBCard>
                        </MDBContainer>
                            <ManagerLayout/>
                        </div>
                    )
                    }
                </div>
            </td>
        </div>
    );
}