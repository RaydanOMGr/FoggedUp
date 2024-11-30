# FoggedUp

Gain precise control over the fog settings in your Minecraft server or map!

## Download

Currently, you can download FoggedUp from the [GitHub Actions page](https://github.com/RaydanOMGr/FoggedUp/actions).  
In the future, the mod will also be available on CurseForge and Modrinth.

## Usage

Use the `/setfog` command to configure fog settings dynamically:

- **Set Fog Shape:**  
  `/setfog shape <player> <shape>`  
  The shape options will be suggested automatically.

- **Set Fog Start Distance:**  
  `/setfog start <player> <float_value>`  
  Adjusts the starting point of the fog.

- **Set Fog End Distance:**  
  `/setfog end <player> <float_value>`  
  Adjusts the ending point of the fog.

### Examples
1. Set a spherical fog for player "Steve":  
   `/setfog shape Steve sphere`
2. Set the fog start distance for player "Alex" to 5.0:  
   `/setfog start Alex 5.0`
3. Set the fog end distance for all players to 20.0:  
   `/setfog end @a 20.0`

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).  
Contributions to this repository are not accepted currently.