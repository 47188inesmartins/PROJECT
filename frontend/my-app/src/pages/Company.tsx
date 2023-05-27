import {Fetch} from "../useFetch";
import React, {useState} from "react";

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

export function Company() {

    const [selectedTime, setSelectedTime] = useState('');

    const handleTimeChange = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setSelectedTime(event.target.value);
        // Faça algo com o valor selecionado
    };

    const params = useParams()
    const id = params.id
    const company = Fetch(`/company/${id}`, "GET");

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
                                        <MDBCardImage src="https://mdbcdn.b-cdn.net/img/Photos/Lightbox/Original/img%20(112).webp"
                                                      alt="image 1" className="w-100 rounded-3" />
                                    </MDBCol>
                                    <MDBCol className="mb-2">
                                        <MDBCardImage src="https://mdbcdn.b-cdn.net/img/Photos/Lightbox/Original/img%20(107).webp"
                                                      alt="image 1" className="w-100 rounded-3" />
                                    </MDBCol>
                                </MDBRow>
                                <MDBRow className="g-2">
                                    <MDBCol className="mb-2">
                                        <MDBCardImage src="https://mdbcdn.b-cdn.net/img/Photos/Lightbox/Original/img%20(108).webp"
                                                      alt="image 1" className="w-100 rounded-3" />
                                    </MDBCol>
                                    <MDBCol className="mb-2">
                                        <MDBCardImage src="https://mdbcdn.b-cdn.net/img/Photos/Lightbox/Original/img%20(114).webp"
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
                                            {company.response.service.map((object: any) => (
                                                <MDBCardText>{object.name}</MDBCardText>
                                            ))}
                                        </div>
                                    </div>
                                    <div className="mb-5">
                                        <p className="lead fw-normal mb-1">Schedule with this company</p>
                                        <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
                                            <div>
                                                <input type="time" step="1800" value={selectedTime} onChange={handleTimeChange} />
                                            </div>
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