#!/usr/bin/env ruby

SIZE = 1_000

class Claim
  attr_reader :id, :startX, :startY, :width, :height

  def initialize(id, startX, startY, width, height)
    @id = id
    @startX = startX
    @startY = startY
    @width = width
    @height = height
  end
end

@prototype_fabric = Array.new(SIZE) { Array.new(SIZE, 0) }
@claims = []

File.readlines('input.txt').each do |line|
  pattern = /\#(?<id>\w+)\s\@\s(?<x>\w+),(?<y>\w+):\s(?<width>\w+)x(?<height>\w+)/.match(line)

  claim = Claim.new(pattern[:id].to_i, pattern[:x].to_i, pattern[:y].to_i,
                    pattern[:width].to_i, pattern[:height].to_i)

  @claims << claim

  (0...claim.width).each do |w|
    (0...claim.height).each do |h|
      @prototype_fabric[claim.startX + w][claim.startY + h] += 1
    end
  end
end

puts @prototype_fabric.flatten.count { |x| x > 1 }

@claims.each do |claim|
  overlap = false

  (0...claim.width).each do |w|
    (0...claim.height).each do |h|
      overlap = true if @prototype_fabric[claim.startX + w][claim.startY + h] > 1
    end
  end

  puts claim.id unless overlap
end
