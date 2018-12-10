#!/usr/bin/env ruby

def manhattan_distance(coordinates1, coordinates2)
  (coordinates1[0] - coordinates2[0]).abs +
    (coordinates1[1] - coordinates2[1]).abs
end

def fixed_location?(coordinates_x, coordinates_y)
  @map_coordinates.any? do |c|
    c[0] == coordinates_x && c[1] == coordinates_y
  end
end

def boundary_locations
  (@array_coordinates[0] +
    @array_coordinates[@max_y] +
    (0..@max_y).collect do |y|
      [@array_coordinates[y][0], @array_coordinates[y][@max_x]]
    end).flatten.uniq
end

coordinates = File.readlines('input.txt').sort.map { |c| c.split.map(&:to_i) }

@map_coordinates = {}

coordinates.each_with_index do |c, index|
  @map_coordinates[index] = c
end

@min_x, @max_x = coordinates.minmax_by { |x| x[0] }.map(&:first)
@min_y, @max_y = coordinates.minmax_by { |x| x[1] }.map(&:last)

@array_coordinates = Array.new(@max_y + 1) { Array.new(@max_x + 1, '.') }

@map_coordinates.each do |index, c|
  @array_coordinates[c[1]][c[0]] = index
end

(0..@max_y).each do |y|
  (0..@max_x).each do |x|
    next if fixed_location?(x, y)

    min_distance = Float::INFINITY
    closest_location = nil

    @map_coordinates.each do |location, c|
      next if min_distance < manhattan_distance(c, [x, y])
      if min_distance == manhattan_distance(c, [x, y])
        closest_location = '.'
      else
        min_distance = manhattan_distance(c, [x, y])
        closest_location = location
      end
    end

    @array_coordinates[y][x] = closest_location
  end
end

rejected_locations = [boundary_locations + ['.']].flatten

max_area = @array_coordinates.flatten.reject { |x| rejected_locations.include?(x) }
                             .each_with_object(Hash.new(0)) { |v, h| h[v] += 1 }
                             .max_by { |v| v[1] }[1]

puts 'PART 1'
puts "Solution: #{max_area}"

region_size = 0

(0..@max_y).each do |y|
  (0..@max_x).each do |x|
    manhattan_distance = @map_coordinates.map { |_c, v| manhattan_distance(v, [x, y]) }.sum
    region_size += 1 if manhattan_distance < 10_000
  end
end

puts 'PART 2'
puts "Solution: #{region_size}"
