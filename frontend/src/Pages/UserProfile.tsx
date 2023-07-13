import React, { useState } from "react";
import {
    MDBCol,
    MDBContainer,
    MDBRow,
    MDBCard,
    MDBCardText,
    MDBCardBody,
    MDBCardImage,
    MDBBreadcrumb,
    MDBBreadcrumbItem,
    MDBInput,
} from "mdb-react-ui-kit";
import { Fetch } from "../Utils/useFetch";
import { useContext } from "react";
import { LoggedInContextCookie } from "./Authentication/Authn";

export function ProfilePage() {
    const role = useContext(LoggedInContextCookie).loggedInState.role;
    const roleCheck = role.indexOf("manager") === -1 && role.indexOf("employee") === -1;
    const check = false //(token !== "GUEST" && token !== "CLIENT")
    const response = Fetch("/user/info", "GET");

    const [editing, setEditing] = useState(false);
    const [name, setName] = useState(response.response?.name || "");
    const [email, setEmail] = useState(response.response?.email || "");
    const [birthday, setBirthday] = useState(response.response?.birthday || "");
    const [address, setAddress] = useState(response.response?.address || "");
    const [city, setCity] = useState("");
    const [country, setCountry] = useState("");

    const handleEdit = () => {
        setEditing(true);
    };

    const handleSave = () => {
        // Aqui você pode implementar a lógica para salvar as informações editadas
        setEditing(false);
    };

    return (
        <div>
            {!response.response ? (
                <div className="loading">
                    <div className="spinner-border" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>
                </div>
            ) : (
                <section style={{ backgroundColor: "#eee" }}>
                    <MDBContainer className="py-5">
                        <MDBRow>
                            <MDBCol>
                                <MDBBreadcrumb className="bg-light rounded-3 p-3 mb-4">
                                    <MDBBreadcrumbItem>
                                        <a style={{ color: "black", flex: "center" }}>My Profile</a>
                                    </MDBBreadcrumbItem>
                                    <MDBBreadcrumbItem active={editing}>
                                        {editing ? (
                                            <button className="btn btn-link" onClick={handleSave}>
                                                Save
                                            </button>
                                        ) : (
                                            <button className="btn btn-link" onClick={handleEdit}>
                                                Edit
                                            </button>
                                        )}
                                    </MDBBreadcrumbItem>
                                </MDBBreadcrumb>
                            </MDBCol>
                        </MDBRow>
                        <MDBRow>
                            <MDBCol lg="4">
                                <MDBCard className="mb-4">
                                    <MDBCardBody className="text-center">
                                        {response.response.profilePic ? (
                                            <>
                                                <MDBCardImage
                                                    src={`data:image/jpeg;base64,${response.response.profilePic}`}
                                                    alt="avatar"
                                                    className="rounded-circle"
                                                    style={{ width: "150px" }}
                                                    fluid
                                                />
                                                <p className="text-muted mb-1">{name}</p>
                                            </>
                                        ) : (
                                            <>
                                                <MDBCardImage
                                                    src={`https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava${Math.floor(
                                                        Math.random() * 6
                                                    ) + 1}.webp`}
                                                    alt="avatar"
                                                    className="rounded-circle"
                                                    style={{ width: "150px" }}
                                                    fluid
                                                />
                                                <p className="text-muted mb-1">{name}</p>
                                            </>
                                        )}
                                    </MDBCardBody>
                                </MDBCard>
                                {!roleCheck ? (
                                    <>
                                        <MDBCard className="mb-4 mb-md-0">
                                            <MDBCardBody>
                                                <MDBCardText className="mb-4">My wallet </MDBCardText>
                                                <a href={`/company/profits`} className="mb-1" style={{ fontSize: "1.2rem" }}>
                                                    <MDBCardText> Check your profits </MDBCardText>
                                                </a>
                                            </MDBCardBody>
                                        </MDBCard>
                                    </>
                                ) : (
                                    <></>
                                )}
                            </MDBCol>
                            <MDBCol lg="8">
                                <MDBCard className="mb-4">
                                    <MDBCardBody>
                                        <MDBRow>
                                            <MDBCol sm="3">
                                                <MDBCardText>Full Name</MDBCardText>
                                            </MDBCol>
                                            <MDBCol sm="9">
                                                {editing ? (
                                                    <MDBInput
                                                        type="text"
                                                        value={name}
                                                        onChange={(e) => setName(e.target.value)}
                                                    />
                                                ) : (
                                                    <MDBCardText className="text-muted">{name}</MDBCardText>
                                                )}
                                            </MDBCol>
                                        </MDBRow>
                                        <hr />
                                        <MDBRow>
                                            <MDBCol sm="3">
                                                <MDBCardText>Email</MDBCardText>
                                            </MDBCol>
                                            <MDBCol sm="9">
                                                {editing ? (
                                                    <MDBInput
                                                        type="email"
                                                        value={email}
                                                        onChange={(e) => setEmail(e.target.value)}
                                                    />
                                                ) : (
                                                    <MDBCardText className="text-muted">{email}</MDBCardText>
                                                )}
                                            </MDBCol>
                                        </MDBRow>
                                        <hr />
                                        <MDBRow>
                                            <MDBCol sm="3">
                                                <MDBCardText>Birthday</MDBCardText>
                                            </MDBCol>
                                            <MDBCol sm="9">
                                                {editing ? (
                                                    <MDBInput
                                                        type="text"
                                                        value={birthday}
                                                        onChange={(e) => setBirthday(e.target.value)}
                                                    />
                                                ) : (
                                                    <MDBCardText className="text-muted">{birthday}</MDBCardText>
                                                )}
                                            </MDBCol>
                                        </MDBRow>
                                        <hr />
                                        <MDBRow>
                                            <MDBCol sm="3">
                                                <MDBCardText>Address</MDBCardText>
                                            </MDBCol>
                                            <MDBCol sm="9">
                                                {editing ? (
                                                    <>
                                                        <MDBInput
                                                            type="text"
                                                            label="City"
                                                            value={city}
                                                            onChange={(e) => setCity(e.target.value)}
                                                        />
                                                        <MDBInput
                                                            type="text"
                                                            label="Country"
                                                            value={country}
                                                            onChange={(e) => setCountry(e.target.value)}
                                                        />
                                                        <MDBInput
                                                            type="text"
                                                            label="Address"
                                                            value={address}
                                                            onChange={(e) => setAddress(e.target.value)}
                                                        />
                                                    </>
                                                ) : (
                                                    <MDBCardText className="text-muted">{address}</MDBCardText>
                                                )}
                                            </MDBCol>
                                        </MDBRow>
                                    </MDBCardBody>
                                </MDBCard>
                                {check ? (
                                    <MDBRow>
                                        <CompanyRole role={"MANAGER"} Text={"My managing companies"} />
                                        <CompanyRole role={"EMPLOYEE"} Text={"Works for"} />
                                    </MDBRow>
                                ) : (
                                    <></>
                                )}
                            </MDBCol>
                        </MDBRow>
                    </MDBContainer>
                </section>
            )}
        </div>
    );
}

function CompanyRole(props: { role: String; Text: String }) {
    const resp = Fetch(`/company/info?role=${props.role}`, "GET");
    console.log("companies", resp.response);
    if (resp.response) {
        if (resp.response.length === 0) {
            return <></>;
        }
    }
    return (
        <>
            {!resp.response ? (
                <div className="loading">
                    <div className="spinner-border" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>
                </div>
            ) : (
                <MDBCol md="6">
                    <MDBCard className="mb-4 mb-md-0">
                        <MDBCardBody>
                            <MDBCardText className="mb-4">{props.Text}</MDBCardText>
                            {resp.response.map((object: any) => (
                                <a href={`/company/${object.id}/profile`} className="mb-1" style={{ fontSize: "1.2rem" }}>
                                    <MDBCardText>{object.name}</MDBCardText>
                                </a>
                            ))}
                        </MDBCardBody>
                    </MDBCard>
                </MDBCol>
            )}
        </>
    );
}
