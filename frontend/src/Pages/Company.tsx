import * as React from "react";
import {useState} from "react";

import {
    MDBCard,
    MDBContainer,
    MDBCol,
    MDBRow,
    MDBCardText,
    MDBCardBody,
    MDBCardImage,
    MDBTypography
} from "mdb-react-ui-kit";

import {useParams} from "react-router-dom";
import {Fetch} from "../Utils/useFetch";
import ScheduleWithCompany from "./ScheduleWithCompany";

export function Company() {

    const [selectedTime, setSelectedTime] = useState('');


    const handleTimeChange = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setSelectedTime(event.target.value);
    };

    const params = useParams()
    const id = params.id

    const company = Fetch(`/company/${id}`, "GET");
    console.log(company)

    return (
        <div className="gradient-custom-2" style={{ backgroundColor: '#0e4378' }}>
            {!company.response?
                <a>Loading...</a>:
                <MDBContainer className="py-5 h-100">
                    <MDBRow className="justify-content-center align-items-center h-100">
                        <MDBCol lg="9" xl="7">
                            <MDBCard>
                                <MDBRow>
                                    <MDBCol className="mb-2">
                                        <MDBCardImage src={URL.createObjectURL( company.response.path) }
                                                      alt={company.response.path} className="w-100 rounded-3" />
                                    </MDBCol>
                                    <MDBCol className="mb-2">
                                        <MDBCardImage src={`data:image/jpeg;base64,${company.response.path}`}
                                                      alt="image 1" className="w-100 rounded-3" />
                                    </MDBCol>
                                </MDBRow>
                                <MDBRow className="g-2">
                                    <MDBCol className="mb-2">
                                        <MDBCardImage src={`data:image/jpeg;base64,${company.response.path}`}
                                                      alt="image 1" className="w-100 rounded-3" />
                                    </MDBCol>
                                    <MDBCol className="mb-2">
                                        <MDBCardImage src={`data:image/jpeg;base64,${company.response.path}`}
                                                      alt="image 1" className="w-100 rounded-3" />
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
                                                        style={{ paddingTop: '12px', paddingBottom: '12px', textAlign: 'left', backgroundColor: '#8faacf', color: 'white' }}>
                                                        Nome </th>
                                                    <th style={{ paddingTop: '12px', paddingBottom: '12px', textAlign: 'left', backgroundColor: '#8faacf', color: 'white' }}>
                                                        Preço </th>
                                                    <th style={{ paddingTop: '12px', paddingBottom: '12px', textAlign: 'left', backgroundColor: '#8faacf', color: 'white' }}>
                                                        Duração </th>
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
    );
}



