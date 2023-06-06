import {Fetch} from "../Utils/useFetch";
import {MDBListGroup, MDBListGroupItem} from "mdb-react-ui-kit";
import * as React from "react";
import {ListGroup} from "react-bootstrap";
import {Link} from "react-router-dom";
import "../Style/AllCompanies.css"

export function AllCompanies(){
    const resp = Fetch(`/company/info?role=MANAGER`, 'GET')

    console.log("AQUI",resp)
    return (
        <>
            {!resp.response?
                <div className="loading">
                    <div className="spinner-border" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>
                </div>
                :
                <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'flex', height: '100vh' }} >
                        <div style={{ width: 700, padding: 30 }}>
                            <h4>
                                My managing companies
                            </h4>
                            <div className="scrollable-list">
                                <ul className="list-group">
                                    {resp.response.map((object: any) => (
                                        <li className="list-group-item" key={object.id}>
                                            <Link to={`/company/${object.id}/managing`}>{object.name}</Link>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </div>
                </div>

            }
        </>

    )
}

//href={`/company/${object.id}/managing`}
//