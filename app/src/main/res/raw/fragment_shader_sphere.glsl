precision mediump float;
uniform vec4 vColor;
uniform vec2 uCentre;
uniform float radius;

void main() {
//    vec2 center = vec2(uCentre.x, uCentre.y);
//    vec2 position = gl_FragCoord.xy - center;
//    if (length(position) > radius) {
//      discard;
//    }
//    float z = sqrt(radius*radius - position.x*position.x - position.y*position.y);
//    z /= radius;
//
//    gl_FragColor = vec4(vec3(z), 1.);

    gl_FragColor = vColor;
}