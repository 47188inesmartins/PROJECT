import * as React from 'react';
import {
    MDBContainer,
    MDBRow,
    MDBCol,
    MDBCard,
    MDBCardBody,
    MDBInput,
}from 'mdb-react-ui-kit';
import {useEffect, useState} from "react";
import Cookies from 'js-cookie';

interface UserCredentials{
    email: string,
    password: string
}

export function Login() {

    const [email, setEmail] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [redirect, setRedirect] = useState<boolean>(false)

    function fetchLogin(){
        const userCredentials: UserCredentials = {
            email,
            password
        }
        fetch(`/api/user/login`,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userCredentials),
        })
        .then(response => response.json())
        .then(data => {
            Cookies.set('name', data.first, { expires: 7 });
            Cookies.set('roles', {
                company: null,
                role: 'CLIENT'
            }, { expires: 7 });
            setRedirect(true);
        })
        .catch(error => {
            console.error('Ocorreu um erro:', error);
        });

    }

    useEffect(() => {
        if (redirect) {
            setRedirect(false);
        }
    }, [redirect]);

    if (redirect) {
        window.location.href = `/`;
        return(<></>)
    }

    return (
        <div>
            <MDBContainer fluid>
                <MDBRow className='d-flex justify-content-center align-items-center h-100'>
                    <MDBCol col='12'>

                        <MDBCard className='bg-dark text-white my-5 mx-auto'
                                 style={{borderRadius: '1rem', maxWidth: '400px'}}>
                            <MDBCardBody className='p-5 d-flex flex-column align-items-center mx-auto w-100'>
                                <h2 className="fw-bold mb-2 text-uppercase">Login</h2>
                                <p className="text-white-50 mb-5">Please enter your login and password!</p>
                                <MDBInput wrapperClass='mb-4 mx-5 w-100'
                                          labelClass='text-white'
                                          label='Email address'
                                          id='formControlLg'
                                          type='email'
                                          size="lg"
                                          value={email}
                                          onChange={(e) => setEmail(e.target.value)}
                                />
                                <MDBInput wrapperClass='mb-4 mx-5 w-100'
                                          labelClass='text-white'
                                          label='Password'
                                          id='formControlLg'
                                          type='password'
                                          size="lg"
                                          value={password}
                                          onChange={(e) => setPassword(e.target.value)}
                                />
                                <button className="btn btn-outline-light btn-lg px-5" type="submit"
                                        onClick={fetchLogin}>Login
                                </button>
                                <br/>
                                <div>
                                    <p className="mb-0">Don't have an account? <a href="/signup" className="text-white-50 fw-bold">
                                        Sign Up
                                    </a></p>
                                </div>
                            </MDBCardBody>
                        </MDBCard>
                    </MDBCol>
                </MDBRow>
            </MDBContainer>
        </div>
    );
}