import * as React from 'react';
import {
    MDBBtn,
    MDBContainer,
    MDBRow,
    MDBCol,
    MDBCard,
    MDBCardBody,
    MDBInput,
    MDBIcon
} from 'mdb-react-ui-kit';
import { useEffect, useState } from "react";
import { Navigate } from "react-router";
import { LoggedInContextCookie } from "../Authentication/Authn";

/*
data class UserInputDto(
        val email: String,
        val password: String,
        val name: String,
        val birthday: String,
        val availability: String?,
        val street: String,
        val city: String,
        val country: String,
        val companyId: Int?,
        val services: List<Int>?,
        val appointment: List<Int>?,
        val unavailability: List<Int>?,
        val interests: String,
        val profilePic: ByteArray?
){
*/


interface UserCredentials {
    name: string,
    birthday: string
    email: string,
    password: string,
    interests: string,
    street: string,
    city: string,
    country: string,
}

export function Signup() {
    const [name, setName] = useState<string>("")
    const [birthday, setBirthday] = useState<string>("")
    const [city, setCity] = useState<string>("")
    const [country, setCountry] = useState<string>("")
    const [email, setEmail] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const categories = ['BEAUTY', 'LIFESTYLE', 'FITNESS', 'BUSINESS', 'EDUCATION', 'OTHERS'];
    const [interests, setInterests] = useState("");
    const [street, setStreet] = useState("");
    const [data, setData] = useState(undefined);
    const [redirect, setRedirect] = useState(false);
    const [showPassword, setShowPassword] = useState(false); // Estado para controlar a exibição da senha

    const handleCategoryClick = (category) => {
        if (interests.includes(category)) {
            const updatedCategories = interests
                .split(",")
                .filter((cat) => cat !== category)
                .join(",");
            setInterests(updatedCategories);
        } else {
            const updatedCategories = interests
                ? interests + "," + category
                : category;
            setInterests(updatedCategories);
        }
    };

    const token = React.useContext(LoggedInContextCookie).loggedInState.token;

    const handleSubmit = () => {
        const userCredentials: UserCredentials = {
            name,
            birthday,
            email,
            password,
            interests,
            street,
            city,
            country
        }

        console.log("handle submit do sign up!")

        fetch(`/api/user`, {
            method: 'POST',
            body: JSON.stringify(userCredentials),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => response.json())
            .then(data => {
                setData(data);
                setRedirect(true);
            })
            .catch(error => {
                window.alert(error)
                console.error('An error has occurred:', error);
            });
    }

    useEffect(() => {
        if (redirect) {
            setRedirect(false);
        }
    }, [redirect]);

    if (redirect) {
        const userId = data.id
        console.log("datata", data)
        console.log("userId", userId)
        window.location.href = `user/${userId}/upload-pic`
        return <></>;
    }

    return (
        <div>
            <MDBContainer fluid>
                <MDBRow className='d-flex justify-content-center align-items-center h-100'>
                    <MDBCol col='12'>
                        <MDBCard className='bg-dark text-white my-5 mx-auto'
                                 style={{ borderRadius: '1rem', maxWidth: '400px' }}>
                            <MDBCardBody className='p-5 d-flex flex-column align-items-center mx-auto w-100'>
                                <h2 className="fw-bold mb-2 text-uppercase">Signup</h2>
                                <p className="text-white-50 mb-5">Signup to our website!</p>

                                <MDBInput
                                    wrapperClass='mb-4 mx-5 w-100'
                                    labelClass='text-white'
                                    label='Name'
                                    id='formControlLg'
                                    type='text'
                                    size="lg"
                                    value={name}
                                    required={true}
                                    onChange={(e) => {
                                        console.log(e.target.value)
                                        setName(e.target.value)
                                    }}
                                />

                                <MDBInput
                                    wrapperClass='mb-4 mx-5 w-100'
                                    labelClass='text-white'
                                    label='Birthday'
                                    id='formControlLg'
                                    type='date'
                                    size="lg"
                                    value={birthday}
                                    required={true}
                                    onChange={(e) => setBirthday(e.target.value)}
                                />

                                <MDBInput
                                    wrapperClass='mb-4 mx-5 w-100'
                                    labelClass='text-white'
                                    label='Address'
                                    id='formControlLg'
                                    type='email'
                                    size="lg"
                                    value={street}
                                    required={true}
                                    onChange={(e) => {
                                        setStreet(e.target.value)
                                        console.log(e.target.value)
                                    }}
                                />

                                <MDBInput
                                    wrapperClass='mb-4 mx-5 w-100'
                                    labelClass='text-white'
                                    label='Country'
                                    id='formControlLg'
                                    type='text'
                                    size="lg"
                                    value={country}
                                    required={true}
                                    onChange={(e) => setCountry(e.target.value)}
                                />

                                <MDBInput
                                    wrapperClass='mb-4 mx-5 w-100'
                                    labelClass='text-white'
                                    label='City'
                                    id='formControlLg'
                                    type='text'
                                    size="lg"
                                    value={city}
                                    required={true}
                                    onChange={(e) => setCity(e.target.value)}
                                />

                                <MDBInput
                                    wrapperClass='mb-4 mx-5 w-100'
                                    labelClass='text-white'
                                    label='Email address'
                                    id='formControlLg'
                                    type='email'
                                    size="lg"
                                    value={email}
                                    required={true}
                                    onChange={(e) => {
                                        setEmail(e.target.value)
                                        console.log(e.target.value)
                                    }}
                                />

                                <div className='position-relative'>
                                    <MDBInput
                                        wrapperClass='mb-4 mx-5 w-100'
                                        labelClass='text-white'
                                        label='Password'
                                        id='formControlLg'
                                        type={showPassword ? 'text' : 'password'} // Alterna entre texto e senha
                                        size="lg"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                    />
                                    <MDBIcon
                                        icon={showPassword ? 'eye-slash' : 'eye'} // Ícone do olho aberto ou fechado
                                        className='position-absolute top-50 end-0 translate-middle-y pe-3'
                                        style={{ cursor: 'pointer' }}
                                        onClick={() => setShowPassword(!showPassword)} // Alterna a exibição da senha ao clicar no ícone
                                    />
                                </div>

                                <a>What are your interests?</a>

                                <div>
                                    {categories.map((category) => (
                                        <button
                                            key={category}
                                            onClick={() => handleCategoryClick(category)}
                                            style={{
                                                backgroundColor: interests.includes(category) ? "pink" : "white",
                                                padding: "8px 16px",
                                                border: "1px solid pink",
                                                borderRadius: "4px",
                                                marginRight: "8px",
                                                marginBottom: "8px",
                                                cursor: "pointer",
                                            }}
                                        >
                                            {category}
                                        </button>
                                    ))}
                                </div>
                                <br />
                                <button className="btn btn-outline-light btn-lg px-5" type="submit"
                                        onClick={handleSubmit}>Signup
                                </button>
                                <br />
                                <div>
                                    <p className="mb-0">Already have an account? <a href="/login"
                                                                                    className="text-white-50 fw-bold">Login</a></p>
                                </div>
                            </MDBCardBody>
                        </MDBCard>
                    </MDBCol>
                </MDBRow>
            </MDBContainer>
        </div>
    );
}
