import React, { useState } from "react";
import "../../Style/CreatingCompany.css";
import { useParams } from "react-router-dom";
import { Fetch } from "../../Utils/useFetch";
import "react-datepicker/dist/react-datepicker.css";
import {ServiceServices} from "../../Service/ServiceServices";
import {convertMinutesToHHMMSS} from "../../Utils/formater";

export function CreatingServicess() {
    const [isOpen, setIsOpen] = useState(false);
    const [serviceCount, setServiceCount] = useState(1);
    const [showSchedule, setShowSchedule] = useState(-1);
    const [lunchBreak, setLunchBreak] = useState({ beginHour: "", endHour: "" });
    const cid = useParams().id;
    const response = Fetch(`/company/${cid}/employees`, "GET");

    const [services, setServices] = useState([
        { serviceName: "", duration: "", price: 0, users: [] },
    ]);

    const daysOfWeek = ["MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"];

    const [schedules, setSchedules] = useState(
        Array.from({ length: serviceCount }, () =>
            daysOfWeek.map((day) => ({
                weekDays: day,
                beginHour: "",
                endHour: "",
                intervalBegin: "",
                intervalEnd: "",
            }))
        )
    );

    const toggleSchedule = (index) => {
        if (showSchedule === index) {
            setShowSchedule(-1);
        } else {
            setShowSchedule(index);
        }
    };

    const handleTimeChange = (serviceIndex, dayIndex, field, value) => {
        setSchedules((prevSchedules) => {
            const updatedSchedules = [...prevSchedules];
            updatedSchedules[serviceIndex][dayIndex][field] = value;
            return updatedSchedules;
        });
    };

    function Employees(props) {
        const { index } = props;

        if (response.response) {
            if (response.response.length === 0) {
                return <> No employees found </>;
            }
        }

        const handleFeatureChange = (employeeId) => {
            setServices((prevServices) => {
                const updatedServices = [...prevServices];
                const selectedUsers = updatedServices[index].users;

                if (selectedUsers.includes(employeeId)) {
                    updatedServices[index].users = selectedUsers.filter(
                        (id) => id !== employeeId
                    );
                } else {
                    updatedServices[index].users = [...selectedUsers, employeeId];
                }

                return updatedServices;
            });
        };

        return (
            <fieldset style={{ backgroundColor: "white", padding: "10px", color: "black" }}>
                <legend>Employees that perform this service:</legend>
                {response.response ? (
                    <>
                        {response.response.map((employee) => (
                            <div key={employee.id}>
                                <input
                                    type="checkbox"
                                    id={employee.id}
                                    name={employee.id}
                                    value={employee.id}
                                    checked={services[index].users.includes(employee.id)}
                                    onChange={() => handleFeatureChange(employee.id)}
                                />
                                <label htmlFor={employee.id}>{employee.name}</label>
                            </div>
                        ))}
                    </>
                ) : (
                    <></>
                )}
            </fieldset>
        );
    }

    const renderOptions = (index) => {
        const options = [];
        for (let i = 1; i <= 200; i++) {
            options.push(
                <div
                    key={i}
                    className="dropdown-option"
                    onClick={() => handleOptionClick(index, i + 1)}
                >
                    {i + 1} minutes
                </div>
            );
        }
        return options;
    };

    const handleDropdownToggle = () => {
        setIsOpen(!isOpen);
    };

    const handleOptionClick = (index, value) => {
        const updatedServices = [...services];
        updatedServices[index].duration = convertMinutesToHHMMSS(value);
        setServices(updatedServices);
        setIsOpen(false);
    };

    const addService = () => {
        setServices((prevServices) => [
            ...prevServices,
            {
                serviceName: "",
                duration: "",
                price: 0,
                users: []
            },
        ]);

        setSchedules((prevSchedules) => [
            ...prevSchedules,
            daysOfWeek.map((day) => ({
                weekDays: day,
                beginHour: "",
                endHour: "",
                intervalBegin: "",
                intervalEnd: "",
            })),
        ]);
        setServiceCount((prevServiceCount) => prevServiceCount + 1);
    };

    const handleServiceChange = (index, field, value) => {
        console.log(field)
        const updatedServices = [...services];
        if (field === "price"){
            updatedServices[index].price = parseFloat(value)
        }
        if (field === "duration") {
            updatedServices[index].duration = convertMinutesToHHMMSS(value);
        } else {
            updatedServices[index][field] = value;
        }
        console.log(updatedServices)
        setServices(updatedServices);
    };

    function fetchCreateServices() {
        const pairs = services.map((service, index) => ({
            first: service,
            second: schedules[index].filter(it => it.beginHour !== '' && it.endHour !== ''),
        }));
        console.log(pairs);
        const requestData = {services: pairs}
        ServiceServices.addServices(cid, requestData)

    }

    return (
        <div className="container rounded bg-white mt-5 mb-5">
            <div className="row">
                <div className="col-md-8 mx-auto">
                    <div className="p-3 py-5">
                        <div className="d-flex justify-content-between align-items-center mb-3">
                            <h4 className="text-right" style={{ color: "black" }}>
                                Add Services
                            </h4>
                        </div>
                        {Array.from({ length: serviceCount }).map((_, index) => (
                            <div key={index} className="service-box rounded border p-3 mb-3">
                                <div className="row">
                                    <div className="col-md-12">
                                        <label className="labels">Service Name</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            placeholder="Service Name"
                                            value={services[index].serviceName}
                                            onChange={(e) =>
                                                handleServiceChange(index, "serviceName", e.target.value)
                                            }
                                        />
                                    </div>
                                    <div className="col-md-12 mt-3">
                                        <div className="dropdown">
                                            <button
                                                className="dropdown-toggle"
                                                onClick={handleDropdownToggle}
                                            >
                                                {services[index].duration
                                                    ? `${services[index].duration} minutes`
                                                    : "Duration (in minutes)"}
                                            </button>
                                            {isOpen && (
                                                <div className="dropdown-options-container">
                                                    <div className="dropdown-options">{renderOptions(index)}</div>
                                                </div>
                                            )}
                                        </div>
                                    </div>
                                    <div className="col-md-12 mt-3">
                                        <label className="labels">Price</label>
                                        <input
                                            type="number"
                                            step="0.01"
                                            className="form-control"
                                            placeholder="Price"
                                            value={services[index].price}
                                            onChange={(e) =>
                                                handleServiceChange(index, "price", e.target.value)
                                            }
                                        />
                                    </div>
                                    <Employees index={index} />
                                    <div className="col-md-12 mt-3">
                                        <button
                                            className="btn btn-outline-primary btn-sm"
                                            onClick={() => toggleSchedule(index)}
                                        >
                                            Personalize Schedule
                                        </button>
                                    </div>
                                    {showSchedule === index && (
                                        <div className="col-md-12 mt-3">
                                            <div className="d-flex justify-content-center">
                                                <table className="schedule-table">
                                                    {schedules[index].map((day, dayIndex) => (
                                                        <tr key={dayIndex}>
                                                            <th style={{ color: "black" }}>{day.weekDays}</th>
                                                            <td>
                                                                <input
                                                                    type="time"
                                                                    value={day.beginHour}
                                                                    onChange={(e) =>
                                                                        handleTimeChange(index, dayIndex, "beginHour", e.target.value)
                                                                    }
                                                                    step="600"
                                                                />
                                                            </td>
                                                            <td>
                                                                <input
                                                                    type="time"
                                                                    value={day.endHour}
                                                                    min={day.beginHour}
                                                                    onChange={(e) =>
                                                                        handleTimeChange(index, dayIndex, "endHour", e.target.value)
                                                                    }
                                                                    step="600"
                                                                />
                                                            </td>
                                                        </tr>
                                                    ))}
                                                    <tr>
                                                        <th style={{ color: "black" }}>Lunch Break</th>
                                                        <td>
                                                            <input
                                                                type="time"
                                                                value={schedules[index][0].intervalBegin}
                                                                onChange={(e) =>
                                                                    handleTimeChange(index, 0, "intervalBegin", e.target.value)
                                                                }
                                                                step="600"
                                                            />
                                                        </td>
                                                        <td>
                                                            <input
                                                                type="time"
                                                                value={schedules[index][0].intervalEnd}
                                                                min={lunchBreak.beginHour}
                                                                onChange={(e) =>
                                                                    handleTimeChange(index, 0, "intervalEnd", e.target.value)
                                                                }
                                                                step="600"
                                                            />
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                    )}
                                </div>
                            </div>
                        ))}
                        <button
                            className="btn btn-outline-primary btn-sm mt-3"
                            onClick={addService}
                        >
                            <i className="fa fa-plus"></i>&nbsp; Add Service
                        </button>
                        <div className="mt-5 text-center">
                            <button
                                className="btn btn-outline-dark btn-lg px-5"
                                type="submit"
                                onClick={fetchCreateServices}
                            >
                                Next
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
