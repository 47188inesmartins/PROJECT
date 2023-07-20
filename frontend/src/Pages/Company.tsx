import * as React from "react";

import {
    MDBCard,
    MDBContainer,
    MDBCol,
    MDBRow,
    MDBCardText,
    MDBCardBody,
    MDBTypography
} from "mdb-react-ui-kit";

import {useParams} from "react-router-dom";
import {Fetch} from "../Utils/useFetch";
import ScheduleWithCompany from "./ScheduleWithCompany";
import {Layout, LayoutRight} from "./Layout";

export function Company() {

    const params = useParams()
    const id = params.id

    const company = Fetch(`/company/${id}`, "GET");
    console.log(company)
    const contentStyle = {
        marginLeft: '200px',
        backgroundColor: '#EFEEEE',
        padding: '20px',
    };
    return (
        <div  style = {contentStyle}>
            <div className="sidebar-left" >
                <Layout />
            </div>
        <div className="gradient-custom-2" style={{ backgroundColor: '#EFEEEE',margin:'20px', marginRight: '200px' }}>
            {!company.response?
                <a>Loading...</a>:
                <MDBContainer className="py-5 h-100">
                    <MDBRow className="justify-content-center align-items-center h-100">
                        <MDBCol lg="9" xl="7">
                            <MDBCard>
                                <MDBRow>
                                    <MDBCol className="mb-2">
                                        <img
                                            src={`data:image/jpeg;base64,${company.response.path[0]}`}
                                            className="img-fluid shadow-1-strong rounded"
                                            alt="Hollywood Sign on The Hill"
                                            style={{
                                                width: '100%',
                                                height: '200px',
                                                objectFit: 'cover',
                                            }}
                                        />
                                    </MDBCol>
                                    <MDBCol className="mb-2">
                                        <img
                                            src={`data:image/jpeg;base64,${company.response.path[1]}`}
                                            className="img-fluid shadow-1-strong rounded"
                                            alt="Hollywood Sign on The Hill"
                                            style={{
                                                width: '100%',
                                                height: '200px',
                                                objectFit: 'cover',
                                            }}
                                        />
                                    </MDBCol>
                                </MDBRow>
                                <MDBRow className="g-2">
                                    <MDBCol className="mb-2">
                                        <img
                                            src={`data:image/jpeg;base64,${company.response.path[2]}`}
                                            className="img-fluid shadow-1-strong rounded"
                                            alt="Hollywood Sign on The Hill"
                                            style={{
                                                width: '100%',
                                                height: '200px',
                                                objectFit: 'cover',
                                            }}
                                        />
                                    </MDBCol>
                                    <MDBCol className="mb-2">
                                        <img
                                            src={`data:image/jpeg;base64,${company.response.path[3]}`}
                                            className="img-fluid shadow-1-strong rounded"
                                            alt="Hollywood Sign on The Hill"
                                            style={{
                                                width: '100%',
                                                height: '200px',
                                                objectFit: 'cover',
                                            }}
                                        />
                                    </MDBCol>
                                </MDBRow>
                                <div>
                                    <MDBTypography tag="h5">{company.response.name}</MDBTypography>
                                </div>
                                <MDBCardBody className="text-black p-4">
                                    <div className="mb-5">
                                        <p className="lead fw-normal mb-1">About</p>
                                        <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
                                            <MDBCardText className="font-italic mb-1">{company.response.description}</MDBCardText>
                                        </div>
                                    </div>
                                    <div className="mb-5">
                                        <p className="lead fw-normal mb-1">Services</p>
                                        <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
                                            <table style={{ fontFamily: 'Arial, Helvetica, sans-serif', borderCollapse: 'collapse', width: '100%' }}>
                                                <thead>
                                                <tr>
                                                    <th
                                                        style={{ paddingTop: '12px', paddingBottom: '12px', textAlign: 'left', backgroundColor: "#8faacf", color: 'white' }}>
                                                        Service </th>
                                                    <th style={{ paddingTop: '12px', paddingBottom: '12px', textAlign: 'left', backgroundColor: '#8faacf', color: 'white' }}>
                                                        Price </th>
                                                    <th style={{ paddingTop: '12px', paddingBottom: '12px', textAlign: 'left', backgroundColor: '#8faacf', color: 'white' }}>
                                                        Duration </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                {company.response.service.map((object: any) => (
                                                    <tr key={object.name}>
                                                        <td style={{border: '1px solid #ddd',
                                                            padding: '8px',
                                                            backgroundColor: '#dadde0'}}> {object.name} </td>
                                                        <td style={{border: '1px solid #ddd',
                                                            padding: '8px',
                                                            backgroundColor: '#dadde0'}}> {object.price} </td>
                                                        <td style={{border: '1px solid #ddd',
                                                            padding: '8px',
                                                            backgroundColor: '#dadde0'}}> {object.duration} </td>
                                                    </tr>
                                                ))}
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div className="mb-5">
                                        <p className="lead fw-normal mb-1">Schedule with this company</p>
                                        <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
                                            <ScheduleWithCompany/>
                                        </div>
                                    </div>
                                </MDBCardBody>
                            </MDBCard>
                        </MDBCol>
                    </MDBRow>
                </MDBContainer>
            }
        </div>
            <LayoutRight/>
        </div>
    );
}



