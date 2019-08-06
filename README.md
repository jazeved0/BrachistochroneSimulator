# [![Brachistichrone Simulator](https://i.imgur.com/pAzA0Ik.png)](https://jazevedo.me/projects/brachistochrone-simulator)

> Iterative calculus-based simulation with accompanying [OpenGL-powered](https://www.lwjgl.org/) graphics display program designed to verify relative performance of different solutions to the historic [Brachistochrone problem](http://mathworld.wolfram.com/BrachistochroneProblem.html). An accompanying report was written in LaTeX which gives simulation results and a mathematical explanation of a rigorous solution.

![Demo](https://jazevedo.me/projects/brachistochrone-simulator/gif.gif)

## 🏁 Results

With a *Δx* of 5 meters and a *Δy* of -3 meters, the following results were derived for the time it takes for the mass to get from *A* to *B*, *t<sub>AB</sub>*, in seconds: (Note that for the final results, an extremely small *dt* was used in order to attain a high level of approximation: for this, *dt* = 1/960,000 sec or 1.04167 × 10<sup>-6</sup>)

| Curve           | *t<sub>AB</sub>* (sec) |
| --------------- | ---------------------- |
| `StraightTrack` | 1.5116                 |
| `CircularTrack` | 1.2679                 |
| `CycloidArc`    | 1.2616                 |

Using basic physics calculations, the time it takes for an object to travel from the start of a 5 × 3 meter frictionless ramp can be found to be approximately __1.52031 seconds__, making the predictions derived via simulation supposedly within only a __0.57%__ error.

## 📃 Write-Up

A LaTeX document detailing the Brachistochrone problem, one of its solutions, and the results of the computational model is [availible here](https://jazevedo.me/projects/brachistochrone-simulator/report.pdf). It was originally produced to be pasted onto a poster for the [UTC Mathematics Poster Competition](https://www.utc.edu/mathematics/pdfs/poster-competition-rules-2018.pdf) in 2018.

## 🔗 Dependencies

- [LWJGL](https://www.lwjgl.org/) - OpenGL bindings and lightweight game engine for Java
- [PNGDecoder](http://wiki.lwjgl.org/wiki/Loading_PNG_images_with_TWL's_PNGDecoder.html) - Utility class used for loading `.png` images into OpenGL for rendering
