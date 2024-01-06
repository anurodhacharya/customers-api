const UserProfile = ({ name, age, gender, imageNumber, ...props }) => {

    // if (gender == "MALE") {
    //     gender = "men";
    // } else {
    //     gender = "women";
    // }

    gender = gender == "MALE" ? "men" : "women";

    // const { name, age, gender } = props;
    return (
        <div>
            <h1>{ name }</h1>
            <p>{ age }</p>
            <img src={`https://randomuser.me/api/portraits/${gender}/${imageNumber}.jpg`}></img>
            { props.children }
        </div>
    )
}

export default UserProfile;
/*
const UserProfile = (props) => {
    return (
        <div>
            <h1>{ props.name }</h1>
            <p>{ props.age }</p>
            <img src={`https://randomuser.me/api/portraits/${props.gender}/75.jpg`}></img>
        </div>
    )
}

export default UserProfile;*/
